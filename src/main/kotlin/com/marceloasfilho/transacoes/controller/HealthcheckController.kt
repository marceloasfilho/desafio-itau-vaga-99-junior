package com.marceloasfilho.transacoes.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/healthcheck")
class HealthcheckController() {

    @GetMapping
    fun healthCheck(): ResponseEntity<Map<String, Any>> {
        val response = mapOf(
            "status" to "UP",
            "timestamp" to Instant.now()
        )
        return ResponseEntity(response, HttpStatus.OK)
    }
}