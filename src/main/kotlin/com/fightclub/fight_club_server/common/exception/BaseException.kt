package com.fightclub.fight_club_server.common.exception

import com.fightclub.fight_club_server.common.constants.ResponseCode

open class BaseException(
    private val responseCode: ResponseCode
) : RuntimeException(responseCode.message) {
    val errorCode: ResponseCode = responseCode
}