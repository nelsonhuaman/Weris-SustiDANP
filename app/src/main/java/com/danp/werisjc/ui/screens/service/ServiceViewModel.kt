package com.danp.werisjc.ui.screens.service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danp.werisjc.domain.model.Post
import com.danp.werisjc.domain.model.Service
import com.danp.werisjc.domain.repository.ServiceRepository
import com.danp.werisjc.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val repository: ServiceRepository,
    private val postRepository: PostRepository
) : ViewModel() {

    private val _allServices = MutableStateFlow<List<Service>>(emptyList())
    val allServices: StateFlow<List<Service>> = _allServices.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val filteredServices = combine(_allServices, _searchQuery) { services, query ->
        if (query.isBlank()) services
        else services.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            _allServices.value = repository.getAllServices()
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
    }

    suspend fun getServiceById(id: String): Service? {
        return repository.getServiceById(id)
    }
    suspend fun getPostsForService(serviceId: String): List<Post> {
        return postRepository.getAllPosts().filter { it.serviceId == serviceId }
    }
}