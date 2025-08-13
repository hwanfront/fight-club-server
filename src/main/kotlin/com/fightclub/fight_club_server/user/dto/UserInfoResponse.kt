package com.fightclub.fight_club_server.user.dto

import com.fightclub.fight_club_server.user.domain.AuthProvider
import com.fightclub.fight_club_server.user.domain.UserRole
import com.fightclub.fight_club_server.user.domain.UserStatus

data class UserInfoResponse (
    var email: String,
    val username: String,
    val nickname: String,
    val providerId: String?,
    var provider: AuthProvider,
    var role: UserRole,
    var status: UserStatus,
    var profileImageUrl: String?,
)
