package com.fightclub.fight_club_server.meta.dto

data class WeightClassResponse(
    val name: String,
    val displayName: String,
    val upperLimit: Double?
)