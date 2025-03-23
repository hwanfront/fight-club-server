package com.fightclub.fight_club_server.auth.dto

data class RefreshResponse (
    val accessToken: String,
    val refreshToken: String
)
