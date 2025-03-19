package com.fightclub.fight_club_server.common.constants

import org.springframework.http.HttpStatus

interface ResponseCode {
    val status: HttpStatus
    val code: String
    val message: String
}