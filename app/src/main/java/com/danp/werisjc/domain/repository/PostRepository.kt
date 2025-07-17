package com.danp.werisjc.domain.repository

import com.danp.werisjc.domain.model.Post

interface PostRepository {
    suspend fun getAllPosts(): List<Post>
    suspend fun getPostById(id: String): Post?
    suspend fun getPostsByServiceId(serviceId: String): List<Post>
}