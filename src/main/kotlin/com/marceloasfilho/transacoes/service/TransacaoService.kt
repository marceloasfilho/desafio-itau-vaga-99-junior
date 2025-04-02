package com.marceloasfilho.transacoes.service

import com.marceloasfilho.transacoes.mapper.toEntity
import com.marceloasfilho.transacoes.model.Transacao
import com.marceloasfilho.transacoes.model.dto.EstatisticaDTO
import com.marceloasfilho.transacoes.model.dto.TransacaoDTO
import com.marceloasfilho.transacoes.repository.TransacaoRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {}

@Service
class TransacaoService(
    private val transacaoRepository: TransacaoRepository,
    private val environment: Environment
) {

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

        val intervalo: Long = this.environment.getProperty("transacao.intervalo", Long::class.java, 1L)
        require(intervalo >= 0) { "O valor de transacao.intervalo não pode ser negativo. Valor atual: $intervalo" }

        var resultado: EstatisticaDTO? = null

        val tempoExecucao = measureTimeMillis {
            val ultimasTransacoes = this.transacaoRepository.listarUltimasTransacoesIntervalo(intervalo)
            logger.info { "Últimas ${ultimasTransacoes.size} transações no(s) último(s) $intervalo minuto(s): $ultimasTransacoes" }

            resultado = if (ultimasTransacoes.isEmpty()) {
                EstatisticaDTO()
            } else {
                EstatisticaDTO(
                    count = ultimasTransacoes.count(),
                    sum = ultimasTransacoes.sumOf { it.valor },
                    avg = ultimasTransacoes.sumOf { it.valor } / ultimasTransacoes.size,
                    min = ultimasTransacoes.minOf { it.valor },
                    max = ultimasTransacoes.maxOf { it.valor }
                )
            }
        }

        logger.info { "Tempo de obtenção das estatísticas: $tempoExecucao ms" }
        return resultado!!
    }
}