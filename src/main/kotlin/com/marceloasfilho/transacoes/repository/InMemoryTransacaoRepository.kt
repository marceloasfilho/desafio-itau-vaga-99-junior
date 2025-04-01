package com.marceloasfilho.transacoes.repository

import com.marceloasfilho.transacoes.model.Transacao
import org.springframework.stereotype.Repository

@Repository
class InMemoryTransacaoRepository : TransacaoRepository {

    private val transacoes = mutableListOf<Transacao>()

    override fun salvar(transacao: Transacao): Transacao {
        transacoes.add(transacao)
        return transacoes.last()
    }

    override fun deletarTodasTransacoes() {
        this.transacoes.clear()
    }

}