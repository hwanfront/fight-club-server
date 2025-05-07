package com.fightclub.fight_club_server.meta.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MetaErrorCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    WEIGHT_CLASS_NOT_FOUND(HttpStatus.NOT_FOUND,"MTE001", "체급이 존재하지 않습니다."),

}