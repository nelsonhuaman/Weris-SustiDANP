package com.danp.werisjc.data.repository

import com.danp.werisjc.data.remote.api.PostApi
import com.danp.werisjc.domain.model.Post
import com.danp.werisjc.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val api: PostApi
) : PostRepository {

    private var cachedPosts: List<Post>? = null

    override suspend fun getAllPosts(): List<Post> {
        if (cachedPosts == null) {
            cachedPosts = api.getAllPosts()
        }
        return cachedPosts ?: emptyList()
    }

    override suspend fun getPostById(id: String): Post? {
        return getAllPosts().find { it.id == id }
    }
    override suspend fun getPostsByServiceId(serviceId: String): List<Post> {
        return getAllPosts().filter { it.serviceId == serviceId }
    }

}