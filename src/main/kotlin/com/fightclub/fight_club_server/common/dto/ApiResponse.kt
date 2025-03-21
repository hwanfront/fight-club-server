package com.fightclub.fight_club_server.common.dto

import com.fightclub.fight_club_server.common.constants.CommonSuccessCode
import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
    val status: Int,
    val code: String,
    val message: String,
    val data: T? = null
) {
    // companion object 는 static 역할을 하는데
    // 싱글톤처럼 활용 가능, 싱글톤 객체로 변환
    // 인터페이스에서 사용 가능
    companion object {
        fun <T> success(responseCode: ResponseCode = CommonSuccessCode.OK, data: T?): ResponseEntity<ApiResponse<T>> {
            return responseEntity(responseCode, data)
        }

        fun error(responseCode: ResponseCode): ResponseEntity<ApiResponse<Nothing>> {
            return responseEntity(responseCode, null)
        }

        fun error(responseCode: ResponseCode, errors: Map<String, String>): ResponseEntity<ApiResponse<Map<String, String>>> {
            return responseEntity(responseCode, errors)
        }

        private fun <T> responseEntity(responseCode: ResponseCode, data: T?): ResponseEntity<ApiResponse<T>> {
            return ResponseEntity.status(responseCode.status).body(
                ApiResponse(
                    status = responseCode.status.value(),
                    code = responseCode.code,
                    message = responseCode.message,
                    data = data
                )
            )
        }
    }
}