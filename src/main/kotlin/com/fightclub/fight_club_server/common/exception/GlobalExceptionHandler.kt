package com.fightclub.fight_club_server.common.exception

import com.fightclub.fight_club_server.common.constants.CommonErrorCode
import com.fightclub.fight_club_server.common.dto.BaseResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<BaseResponse<Nothing>> {
        return BaseResponse.error(e.errorCode)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<BaseResponse<Map<String, String>>> {
        val errorMessages = e.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "잘못된 값입니다.") }
        return BaseResponse.error(CommonErrorCode.BAD_REQUEST, errorMessages)
    }
}