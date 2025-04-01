package com.marceloasfilho.transacoes.controller

import com.marceloasfilho.transacoes.model.dto.EstatisticaDTO
import com.marceloasfilho.transacoes.model.dto.TransacaoDTO
import com.marceloasfilho.transacoes.service.TransacaoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TransacaoController(private val transacaoService: TransacaoService) {

    @PostMapping("/transacao")
    fun recebeTransacao(@Valid @RequestBody transacao: TransacaoDTO): ResponseEntity<Void> {
        this.transacaoService.salvaTransacao(transacao)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("/transacao")
    fun deletaTodasTransacoes(): ResponseEntity<Void> {
        this.transacaoService.deletaTodasTransacoes()
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping
    fun obterEstatisticasTransacoes(): ResponseEntity<EstatisticaDTO> {
        val response = this.transacaoService.obtemEstatisticasTransacoes()
        return ResponseEntity(response, HttpStatus.OK)
    }
}