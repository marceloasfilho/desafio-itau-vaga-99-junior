package com.marceloasfilho.transacoes.controller

import com.marceloasfilho.transacoes.model.dto.EstatisticaDTO
import com.marceloasfilho.transacoes.model.dto.TransacaoDTO
import com.marceloasfilho.transacoes.service.TransacaoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Transações")
class TransacaoController(private val transacaoService: TransacaoService) {

    @Operation(
        summary = "Endpoint para adição de uma nova transação",
        description = "Recebe uma transação e, se válida, adiciona em memória"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Transação adicionada com sucesso")
        ]
    )
    @PostMapping("/transacao")
    fun recebeTransacao(
        @Parameter(
            name = "transacao",
            description = "Transação a ser validada",
            required = true,
        ) @Valid @RequestBody transacao: TransacaoDTO
    ): ResponseEntity<Void> {
        this.transacaoService.salvaTransacao(transacao)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @Operation(
        summary = "Endpoint para exclusão de transações",
        description = "Deleta todas as transações salvas em memória"
    )
    @DeleteMapping("/transacao")
    fun deletaTodasTransacoes(): ResponseEntity<Void> {
        this.transacaoService.deletaTodasTransacoes()
        return ResponseEntity(HttpStatus.OK)
    }

    @Operation(
        summary = "Endpoint de obtenção de estatísticas",
        description = "Retorna estatísticas das transações que aconteceram num determinado período"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Transação adicionada com sucesso",
                content = [Content(
                    schema = Schema(implementation = EstatisticaDTO::class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE
                )]
            ),
        ]
    )
    @GetMapping("/estatistica")
    fun obterEstatisticasTransacoes(): ResponseEntity<EstatisticaDTO> {
        val response = this.transacaoService.obtemEstatisticasTransacoes()
        return ResponseEntity(response, HttpStatus.OK)
    }
}