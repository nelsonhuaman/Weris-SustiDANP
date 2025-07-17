package com.danp.werisjc.ui.screens.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danp.werisjc.domain.model.Post
import com.danp.werisjc.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val allPosts = mutableListOf<Post>()
    private var currentPage = 0
    private val pageSize = 10

    private val _visiblePosts = MutableStateFlow<List<Post>>(emptyList())
    val visiblePosts: StateFlow<List<Post>> = _visiblePosts.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            val posts = repository.getAllPosts()
            allPosts.addAll(posts)
            loadNextPage()
        }
    }

    fun loadNextPage() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            val filtered = filterPosts(allPosts, _searchQuery.value)
            val start = currentPage * pageSize
            val end = (start + pageSize).coerceAtMost(filtered.size)
            if (start < end) {
                _visiblePosts.value = _visiblePosts.value + filtered.subList(start, end)
                currentPage++
            }
            _isLoading.value = false
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            val filtered = filterPosts(allPosts, query)
            currentPage = 0
            _visiblePosts.value = emptyList()
            val end = pageSize.coerceAtMost(filtered.size)
            _visiblePosts.value = filtered.subList(0, end)
            currentPage = 1
        }
    }

    private fun filterPosts(posts: List<Post>, query: String): List<Post> {
        return if (query.isBlank()) posts
        else posts.filter {
            it.label.contains(query, ignoreCase = true) ||
                    it.message.contains(query, ignoreCase = true)
        }
    }

    suspend fun getPostById(id: String): Post? {
        return repository.getPostById(id)
    }

    suspend fun getPostsByServiceId(serviceId: String): List<Post> {
        return repository.getPostsByServiceId(serviceId)
    }
}
