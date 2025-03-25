package com.fightclub.fight_club_server.meta.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MetaSuccessCode (
    override val status: HttpStatus,
    override val code: String,
    override val message: String
) : ResponseCode {
    WEIGHT_CLASSES_SUCCESS(HttpStatus.OK, "MTS001", "체급 리스트 요청 성공"),
}