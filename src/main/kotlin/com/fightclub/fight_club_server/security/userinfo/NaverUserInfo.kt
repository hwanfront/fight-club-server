package com.fightclub.fight_club_server.security.userinfo

import com.fightclub.fight_club_server.user.domain.AuthProvider

class NaverUserInfo : OAuth2UserInfoStrategy {
    override val provider = AuthProvider.NAVER
    override fun extractProviderId(attributes: Map<String, Any>): String {
        val response = attributes["response"] as Map<*, *>
        return response["id"].toString()
    }

    override fun extractEmail(attributes: Map<String, Any>): String {
        val response = attributes["response"] as Map<*, *>
        return response["email"].toString()
    }
}