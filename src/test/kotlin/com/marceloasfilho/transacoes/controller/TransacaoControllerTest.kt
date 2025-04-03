package com.marceloasfilho.transacoes.controller

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.OffsetDateTime
import kotlin.test.Test

class TransacaoControllerTest : BaseTestRestController() {

    @Test
    fun deveRetornar201CreatedQuandoSalvarTransacaoValida() {
        // Cenário
        val payload = """
            {
                "valor": 33.66,
                "dataHora": "2025-04-02T11:18:00.000-03:00"
            }
        """.trimIndent()

        // Ação
        super.mockMvc.perform(
            post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        ).andExpect(status().isCreated)
    }

    @Test
    fun deveRetornar422UnprocessableEntityQuandoTransacaoEnviadaPossuiCamposNulos() {
        // Cenário
        val payload = """
            {
                "naoValor": 33.66,
                "naoDataHora": "2025-04-02T11:18:00.000-03:00"
            }
        """.trimIndent()

        // Ação
        super.mockMvc.perform(
            post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        ).andExpect(status().isUnprocessableEntity)
    }

    @Test
    fun deveRetornar400BadRequestQuandoTransacaoEnviadaPossuiCampoValorNaoNumerico() {
        // Cenário
        val payload = """
            {
                "valor": true,
                "dataHora": "2025-04-02T11:18:00.000-03:00"
            }
        """.trimIndent()

        // Ação
        super.mockMvc.perform(
            post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        ).andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error").value("Malformed JSON request"))

    }

    @Test
    fun deveRetornar422UnprocessableEntityQuandoTransacaoEnviadaPossuiCampoValorNegativo() {
        // Cenário
        val payload = """
            {
                "valor": -13.22,
                "dataHora": "2025-04-02T11:18:00.000-03:00"
            }
        """.trimIndent()

        // Ação
        super.mockMvc.perform(
            post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        ).andExpect(status().isUnprocessableEntity)
    }

    @Test
    fun deveRetornar400BadRequestQuandoTransacaoEnviadaPossuiCampoDataHoraSemFormato() {
        // Cenário
        val payload = """
            {
                "valor": 13.22,
                "dataHora": true
            }
        """.trimIndent()

        // Ação
        super.mockMvc.perform(
            post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        ).andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error").value("Malformed JSON request"))
    }

    @Test
    fun deveRetornar422UnprocessableEntityQuandoTransacaoEnviadaPossuiCampoDataHoraFutura() {
        // Cenário
        val dataHoraFutura = OffsetDateTime.now().plusDays(1)

        val payload = """
            {
                "valor": 13.22,
                "dataHora": "$dataHoraFutura"
            }
        """.trimIndent()

        // Ação
        super.mockMvc.perform(
            post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        ).andExpect(status().isUnprocessableEntity)
    }

    @Test
    fun deveRetornar400BadRequestQuandoPayloadPossuirJsonInvalido() {
        // Cenário
        val payload = """
            {
                "valor": 13.22,
                "dataHora": "2025-04-02T11:18:00.000-03:00",,
            }
        """.trimIndent()

        // Ação
        super.mockMvc.perform(
            post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        ).andExpect(status().isBadRequest)
    }

    @Test
    fun deveRetornar200OkQuandoDeletarTodasTransacoesSemRegistrosEmMemoria() {
        // Ação
        super.mockMvc.perform(
            delete("/transacao")
        ).andExpect(status().isOk)

        super.mockMvc.perform(
            get("/estatistica")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.count").value(0))
            .andExpect(jsonPath("$.sum").value(0.0))
            .andExpect(jsonPath("$.avg").value(0.0))
            .andExpect(jsonPath("$.min").value(0.0))
            .andExpect(jsonPath("$.max").value(0.0))
    }

    @Test
    fun deveRetornar200OkQuandoDeletarTodasTransacoesComRegistrosEmMemoria() {
        val agora = OffsetDateTime.now().minusMinutes(1)
        val payload1 = """
            {
                "valor": 200.0,
                "dataHora": "$agora"
            }
        """.trimIndent()

        val payload2 = """
            {
                "valor": 100.0,
                "dataHora": "$agora"
            }
        """.trimIndent()

        // Ação

        // Salva a primeira transação
        super.mockMvc.perform(
            post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload1)
        ).andExpect(status().isCreated)

        // Salva a segunda transação
        super.mockMvc.perform(
            post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload2)
        ).andExpect(status().isCreated)

        // Obtém as estatísticas das transações salvas em memória
        super.mockMvc.perform(
            get("/estatistica")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.count").value(2))
            .andExpect(jsonPath("$.sum").value(300.0))
            .andExpect(jsonPath("$.avg").value(150.0))
            .andExpect(jsonPath("$.min").value(100.0))
            .andExpect(jsonPath("$.max").value(200.0))

        // Deleta todas as transações
        super.mockMvc.perform(
            delete("/transacao")
        ).andExpect(status().isOk)

        // Obtém valores zerados nas estatísticas
        super.mockMvc.perform(
            get("/estatistica")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.count").value(0))
            .andExpect(jsonPath("$.sum").value(0.0))
            .andExpect(jsonPath("$.avg").value(0.0))
            .andExpect(jsonPath("$.min").value(0.0))
            .andExpect(jsonPath("$.max").value(0.0))
    }
}