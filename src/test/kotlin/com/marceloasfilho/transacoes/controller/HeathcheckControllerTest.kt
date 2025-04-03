package com.marceloasfilho.transacoes.controller

import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HeathcheckControllerTest : BaseTestRestController() {

    @Test
    fun deveRetornar200OkQuandoConsultarSaudeAplicacao() {
        // Ação
        super.mockMvc.perform(
            get("/healthcheck")
        ).andExpect(status().isOk)
    }
}