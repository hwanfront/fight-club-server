package com.fightclub.fight_club_server.auth.dto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
