package com.marceloasfilho.transacoes.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/healthcheck")
@Tag(name = "Health Check")
class HealthcheckController() {

    @GetMapping
    @Operation(
        summary = "Endpoint de Health Check",
        description = "Verificação da saúde da aplicação"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Aplicação UP",
                content = [Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = [
                        ExampleObject(
                            name = "Saúde da Aplicação",
                            value = "{\"status\": \"UP\", \"timestamp\": \"2025-04-02T12:32:41.634460800Z\"}"
                        )
                    ]
                )]
            )
        ]
    )
    fun healthCheck(): ResponseEntity<Map<String, Any>> {
        val response = mapOf(
            "status" to "UP",
            "timestamp" to Instant.now()
        )
        return ResponseEntity(response, HttpStatus.OK)
    }
}