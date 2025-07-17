package com.danp.werisjc.domain.repository

import com.danp.werisjc.domain.model.Service

interface ServiceRepository {
    suspend fun getAllServices(): List<Service>
    suspend fun getServiceById(id: String): Service?
}