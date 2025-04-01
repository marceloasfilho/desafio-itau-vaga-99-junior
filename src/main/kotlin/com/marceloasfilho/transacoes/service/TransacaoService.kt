package com.marceloasfilho.transacoes.service

import com.marceloasfilho.transacoes.model.Transacao
import com.marceloasfilho.transacoes.repository.TransacaoRepository
import org.springframework.stereotype.Service

@Service
class TransacaoService(private val transacaoRepository: TransacaoRepository) {

    fun salvaTransacao(transacao: Transacao): Transacao {
        return this.transacaoRepository.salvar(transacao)
    }

    fun deletaTodasTransacoes() {
        this.transacaoRepository.deletarTodasTransacoes()
    }
}