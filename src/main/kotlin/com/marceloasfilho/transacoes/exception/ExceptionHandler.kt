package com.marceloasfilho.transacoes.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandlers : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any?> {
        val errors: MutableMap<String, String?> = mutableMapOf()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = if (error is FieldError) error.field else error.objectName
            errors[fieldName] = error.defaultMessage
        }

        val responseBody = mapOf(
            "status" to HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "error" to "Unprocessable Entity",
            "messages" to errors
        )
        return ResponseEntity(responseBody, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<in Any>? {
        val response = ErrorMessage(
            message = ex.localizedMessage,
            status = status.value(),
            error = "Malformed JSON request"
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }
}