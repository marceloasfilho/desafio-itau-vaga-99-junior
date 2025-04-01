package com.marceloasfilho.transacoes.controller

import com.marceloasfilho.transacoes.mapper.toEntity
import com.marceloasfilho.transacoes.model.dto.TransacaoDTO
import com.marceloasfilho.transacoes.service.TransacaoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TransacaoController(private val transacaoService: TransacaoService) {

    @PostMapping("/transacao")
    fun recebeTransacao(@Valid @RequestBody transacao: TransacaoDTO): ResponseEntity<Void> {
        val transacaoValida = transacao.toEntity()
        this.transacaoService.salvaTransacao(transacaoValida)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("/transacao")
    fun deletaTodasTransacoes(): ResponseEntity<Void> {
        this.transacaoService.deletaTodasTransacoes()
        return ResponseEntity(HttpStatus.OK)
    }
}