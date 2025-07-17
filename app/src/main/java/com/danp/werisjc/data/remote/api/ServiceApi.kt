package com.danp.werisjc.data.remote.api

import com.danp.werisjc.domain.model.Service
import retrofit2.http.GET

interface ServiceApi {
    @GET("services")
    suspend fun getAllServices(): List<Service>
}