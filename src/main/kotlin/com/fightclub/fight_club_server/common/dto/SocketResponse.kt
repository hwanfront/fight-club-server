package com.fightclub.fight_club_server.common.dto

import com.fightclub.fight_club_server.common.constants.SocketResponseCode

data class SocketResponse<T>(
    override val code: String,
    override val success: Boolean,
    override val message: String,
    override val data: T?,
): SocketResult<T> {
    companion object {
        fun <T> success(responseCode: SocketResponseCode, data: T?): SocketResponse<T> {
            return responseEntity(responseCode, true, data)
        }

        fun error(responseCode: SocketResponseCode): SocketResponse<Nothing> {
            return responseEntity(responseCode, false, null)
        }


        private fun <T> responseEntity(responseCode: SocketResponseCode, success: Boolean, data: T?): SocketResponse<T> {
            return SocketResponse(
                code = responseCode.code,
                success = success,
                message = responseCode.message,
                data = data
            )
        }
    }
}