package com.fightclub.fight_club_server.security.userinfo

import com.fightclub.fight_club_server.user.domain.AuthProvider

class GoogleUserInfo : OAuth2UserInfoStrategy {
    override val provider = AuthProvider.GOOGLE
    override fun extractProviderId(attributes: Map<String, Any>) = attributes["sub"].toString()
    override fun extractEmail(attributes: Map<String, Any>) = attributes["email"].toString()
}