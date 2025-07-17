package com.danp.werisjc.data.repository

import com.danp.werisjc.data.remote.api.ServiceApi
import com.danp.werisjc.domain.model.Service
import com.danp.werisjc.domain.repository.ServiceRepository
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val api: ServiceApi
) : ServiceRepository {

    private var cachedServices: List<Service>? = null

    override suspend fun getAllServices(): List<Service> {
        if (cachedServices == null) {
            cachedServices = api.getAllServices()
        }
        return cachedServices ?: emptyList()
    }

    override suspend fun getServiceById(id: String): Service? {
        return getAllServices().find { it.id == id }
    }

}