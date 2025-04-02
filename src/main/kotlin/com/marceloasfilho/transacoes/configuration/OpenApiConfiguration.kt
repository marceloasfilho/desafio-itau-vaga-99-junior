package com.marceloasfilho.transacoes.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("API de Transações do Desafio Itaú Unibanco - Vaga 99 Júnior")
                    .version("1.0")
                    .description("Documentação da API de Transações do Desafio Itaú Unibanco, utilizando Spring Boot com Kotlin")
            ).servers(
                listOf(
                    Server().url("http://localhost:8080").description("Servidor Local")
                )
            )
    }
}