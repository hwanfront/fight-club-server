package com.fightclub.fight_club_server.matchingWait.dto

interface MatchingCandidateProjection {
    fun getUserId(): Long
    fun getNickname(): String
    fun getWeight(): Double
    fun getWeightClass(): String
}