package com.fightclub.fight_club_server.match.dto

data class ReadChatMessageRequest(
    val matchId: Long,
    val messageId: Long,
)
