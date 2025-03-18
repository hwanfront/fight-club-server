package com.fightclub.fight_club_server.common.exception

import com.fightclub.fight_club_server.common.dto.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<ApiResponse<Nothing>> {
        return ApiResponse.error(e.errorCode)
    }
}