package com.fightclub.fight_club_server.user.mapper

import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserStatus
import com.fightclub.fight_club_server.user.dto.SignupRequest
import com.fightclub.fight_club_server.user.dto.UserInfoResponse
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toUserInfoResponse(user: User) = UserInfoResponse(
        email = user.email ?: "unknown",
        username = user.username ?: "unknown",
        nickname = user.nickname ?: "unknown",
        providerId = user.providerId ?: "N/A",
        provider = user.provider,
        role = user.role,
        status = user.status,
        profileImageUrl = user.profileImageUrl
    )

    fun fromSignupRequest(signupRequest: SignupRequest, encodedPassword: String) = User(
        email = signupRequest.email,
        password = encodedPassword,
        nickname = signupRequest.nickname,
        username = signupRequest.username,
        status = UserStatus.REGISTERED
    )
}