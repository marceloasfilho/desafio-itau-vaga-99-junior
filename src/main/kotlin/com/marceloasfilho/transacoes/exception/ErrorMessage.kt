package com.marceloasfilho.transacoes.exception

import java.time.Instant

data class ErrorMessage(val message: String, val status: Int, val error: String, val timestamp: Instant)