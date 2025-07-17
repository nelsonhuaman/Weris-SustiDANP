package com.danp.werisjc.domain.model

data class Service(
    val id: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val timetable: String,
    val alert: String,
    val img: String,
    val url: String

)