package com.marceloasfilho.transacoes.repository

import com.marceloasfilho.transacoes.model.Transacao


interface TransacaoRepository {
    fun salvar(transacao: Transacao): Transacao
    fun deletarTodasTransacoes()
}