package com.github.manerajona.sendgrid

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SendgridApplication

fun main(args: Array<String>) {
    runApplication<SendgridApplication>(*args)
}
