package com.github.manerajona.sendgrid

import com.sendgrid.SendGrid
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SendGridConfig {

    @Value("\${email.sendgrid.apikey}")
    lateinit var apiKey: String

    @Bean
    fun sendGrid(): SendGrid = SendGrid(apiKey)

}
