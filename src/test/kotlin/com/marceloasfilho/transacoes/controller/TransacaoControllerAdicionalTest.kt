package com.marceloasfilho.transacoes.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@SpringBootTest(properties = ["transacao.intervalo=-1"])
@ActiveProfiles("test")
@AutoConfigureMockMvc
class TransacaoControllerAdicionalTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun deveRetornar400BadRequestAoSetarPropriedadeIntervaloEstatisticaNegativo() {
        this.mockMvc.perform(
            get("/estatistica")
        ).andExpect(status().isBadRequest)
        .andExpect(jsonPath("$.message").value("O valor da propriedade {transacao.intervalo} não pode ser negativo. Valor atual: -1"))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.error").value("Bad Request"))
            .andExpect { jsonPath("$.timestamp").isNotEmpty }
    }
}