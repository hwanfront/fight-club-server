package com.fightclub.fight_club_server.user.dto

import com.fightclub.fight_club_server.user.domain.User

fun User.toUserInfoResponse() = UserInfoResponse(
    email = this.email ?: "unknown",
    username = this.username ?: "unknown",
    nickname = this.nickname ?: "unknown",
    providerId = this.providerId ?: "N/A",
    provider = this.provider,
    role = this.role
)