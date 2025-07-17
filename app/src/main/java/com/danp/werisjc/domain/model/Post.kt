package com.danp.werisjc.domain.model

data class Post(
    val id: String,
    val label: String,
    val message: String,
    val datetime: String,
    val img: String,

    val serviceId: String
)