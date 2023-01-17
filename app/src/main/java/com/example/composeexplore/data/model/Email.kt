package com.example.composeexplore.data.model

import java.util.UUID

data class Email(
    val id: UUID = UUID.randomUUID(),
    val subject: String,
    val message: String,
    val dateTime: String,
)
