package com.marceloasfilho.transacoes.controller

import com.marceloasfilho.transacoes.TransacoesApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(classes = [TransacoesApplication::class])
open class BaseTestRestController {

    @Autowired
    protected lateinit var mockMvc: MockMvc
}