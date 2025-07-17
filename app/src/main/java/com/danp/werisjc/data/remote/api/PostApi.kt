package com.danp.werisjc.data.remote.api

import com.danp.werisjc.domain.model.Post
import retrofit2.http.GET

interface PostApi {
    @GET("posts")
    suspend fun getAllPosts(): List<Post>
}