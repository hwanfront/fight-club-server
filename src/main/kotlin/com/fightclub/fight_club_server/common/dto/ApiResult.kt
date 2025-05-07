package com.fightclub.fight_club_server.common.dto

interface ApiResult<T> {
    val status: Int;
    val code: String;
    val message: String;
    val data: T?;
}