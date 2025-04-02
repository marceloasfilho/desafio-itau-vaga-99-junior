package com.marceloasfilho.transacoes.repository

import com.marceloasfilho.transacoes.model.Transacao
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class InMemoryTransacaoRepository : TransacaoRepository {

    private val transacoes = mutableListOf<Transacao>()

    override fun salvar(transacao: Transacao): Transacao {
        this.transacoes.add(transacao)
        return this.transacoes.last()
    }

    override fun deletarTodasTransacoes() {
        this.transacoes.clear()
    }

    override fun listarUltimasTransacoesIntervalo(intervalo: Long): List<Transacao> {
        val dataHoraFim: OffsetDateTime = OffsetDateTime.now()
        val dataHoraInicio = dataHoraFim.minusMinutes(intervalo)

        return this.transacoes.filter { !it.dataHora.isBefore(dataHoraInicio) && !it.dataHora.isAfter(dataHoraFim) }
    }

}