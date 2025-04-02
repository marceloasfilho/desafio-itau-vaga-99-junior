package com.marceloasfilho.transacoes.service

import com.marceloasfilho.transacoes.exception.InvalidIntervalStatisticException
import com.marceloasfilho.transacoes.model.Transacao
import com.marceloasfilho.transacoes.model.dto.EstatisticaDTO
import com.marceloasfilho.transacoes.model.dto.TransacaoDTO
import com.marceloasfilho.transacoes.repository.TransacaoRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.whenever
import org.springframework.core.env.Environment
import java.time.OffsetDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class TransacaoServiceTest {

    @InjectMocks
    private lateinit var service: TransacaoService

    @Mock
    private lateinit var repository: TransacaoRepository

    @Mock
    private lateinit var environment: Environment

    @BeforeEach
    fun setUp() {
        openMocks(this)
    }

    @Test
    fun deveLancarInvalidIntervalStatisticExceptionAoSetarPropriedadeIntervaloEstatisticaNegativo() {
        // Mock
        whenever(this.environment.getProperty("transacao.intervalo", Long::class.java, 1L)).thenReturn(-1L)

        // Ação/Verificação
        assertThrows<InvalidIntervalStatisticException> {
            this.service.obtemEstatisticasTransacoes()
        }
    }

    @Test
    fun deveRetornarObjetoEstatisticaComValoresPadraoQuandoNaoHouverTransacoesEmMemoria() {
        // Mock
        whenever(this.environment.getProperty("transacao.intervalo", Long::class.java, 1L)).thenReturn(1L)
        whenever(this.repository.listarUltimasTransacoesIntervalo(any<Long>())).thenReturn(listOf())

        // Ação
        val estatistica = this.service.obtemEstatisticasTransacoes()

        // Verificação
        assertNotNull(estatistica)
        assertEquals(EstatisticaDTO(), estatistica)

        assertEquals(0, estatistica.count)
        assertEquals(0.0, estatistica.sum)
        assertEquals(0.0, estatistica.avg)
        assertEquals(0.0, estatistica.min)
        assertEquals(0.0, estatistica.max)
    }

    @Test
    fun deveRetornarObjetoEstatisticaPreenchidosQuandoHouverTransacoesEmMemoria() {
        // Cenário
        val agora = OffsetDateTime.now()

        // Mock
        whenever(this.environment.getProperty("transacao.intervalo", Long::class.java, 1L)).thenReturn(1L)
        whenever(this.repository.listarUltimasTransacoesIntervalo(any())).thenReturn(
            listOf(
                Transacao(100.0, agora.minusSeconds(10)),
                Transacao(200.0, agora.minusSeconds(30))
            )
        )

        // Ação
        val estatistica = this.service.obtemEstatisticasTransacoes()

        // Verificação
        assertNotNull(estatistica)
        assertNotEquals(EstatisticaDTO(), estatistica)

        assertEquals(2, estatistica.count)
        assertEquals(300.0, estatistica.sum)
        assertEquals(150.0, estatistica.avg)
        assertEquals(100.0, estatistica.min)
        assertEquals(200.0, estatistica.max)
    }

    @Test
    fun deveDeletarTodasTransacoesComSucesso() {
        // Ação
        this.service.deletaTodasTransacoes()

        // Verificação
        verify(this.repository, times(1)).deletarTodasTransacoes()
    }

    @Test
    fun deveSalvarTransacaoValidaComSucesso() {
        // Cenário
        val transacao = TransacaoDTO(valor = 110.0, dataHora = OffsetDateTime.now())
        val transacaoResponse = Transacao(valor = transacao.valor!!, dataHora = transacao.dataHora!!)

        // Mock
        whenever(this.repository.salvar(any<Transacao>())).thenReturn(transacaoResponse)

        // Ação
        val transacaoSalva = this.service.salvaTransacao(transacao)

        // Verificação
        assertNotNull(transacaoSalva)
        assertEquals(110.0, transacaoSalva.valor)
        assertNotNull(transacaoSalva.dataHora)
        verify(this.repository, times(1)).salvar(any())
    }
}