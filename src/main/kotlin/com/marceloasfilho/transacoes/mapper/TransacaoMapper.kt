package com.marceloasfilho.transacoes.mapper

import com.marceloasfilho.transacoes.model.Transacao
import com.marceloasfilho.transacoes.model.dto.TransacaoDTO

fun TransacaoDTO.toEntity(): Transacao = Transacao(
    valor = this.valor!!,
    dataHora = this.dataHora!!
)