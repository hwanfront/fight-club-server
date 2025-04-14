package com.fightclub.fight_club_server.common.dto

interface SocketResult<T> {
    val code: String;
    val success: Boolean;
    val message: String;
    val data: T?;
}