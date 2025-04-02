package com.marceloasfilho.transacoes.service

import com.marceloasfilho.transacoes.mapper.toEntity
import com.marceloasfilho.transacoes.model.Transacao
import com.marceloasfilho.transacoes.model.dto.EstatisticaDTO
import com.marceloasfilho.transacoes.model.dto.TransacaoDTO
import com.marceloasfilho.transacoes.repository.TransacaoRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class TransacaoService(private val transacaoRepository: TransacaoRepository) {

    fun salvaTransacao(transacao: TransacaoDTO): Transacao {
        val transacaoValida = transacao.toEntity()
        logger.info { "Transação válida: $transacaoValida" }
        return this.transacaoRepository.salvar(transacaoValida)
    }

    fun deletaTodasTransacoes() {
        this.transacaoRepository.deletarTodasTransacoes()
        logger.info { "Todas as transações foram deletadas com sucesso" }
    }

    fun obtemEstatisticasTransacoes(): EstatisticaDTO {
        val ultimasTransacoes = this.transacaoRepository.listarTransacoesUltimoMinuto()

        logger.info { "Últimas transações ocorridas no último minuto: $ultimasTransacoes" }

        return when (ultimasTransacoes.size) {
            0 -> EstatisticaDTO()
            else -> EstatisticaDTO(
                count = ultimasTransacoes.count(),
                sum = ultimasTransacoes.sumOf { it.valor },
                avg = (ultimasTransacoes.sumOf { it.valor }) / ultimasTransacoes.size,
                min = ultimasTransacoes.minOf { it.valor },
                max = ultimasTransacoes.maxOf { it.valor }
            )
        }
    }
}