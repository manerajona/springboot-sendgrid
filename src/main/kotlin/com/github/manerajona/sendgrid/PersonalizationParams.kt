package com.github.manerajona.sendgrid

data class PersonalizationParams(
    val name: String,
    val image: String,
    val text: String,
    val legal: String,
    val from: String,
    val to: String,
    val replyTo: String
)
