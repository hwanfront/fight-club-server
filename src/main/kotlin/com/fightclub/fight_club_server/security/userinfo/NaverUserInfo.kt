package com.fightclub.fight_club_server.security.userinfo

import com.fightclub.fight_club_server.user.domain.AuthProvider

class NaverUserInfo : OAuth2UserInfoStrategy {
    override val provider = AuthProvider.NAVER
    override fun extractProviderId(attributes: Map<String, Any>) = (attributes["response"] as Map<*, *>)["id"].toString()
    override fun extractEmail(attributes: Map<String, Any>) = (attributes["response"] as Map<*, *>)["email"].toString()
    override fun extractProfileUrl(attributes: Map<String, Any>) = (attributes["response"] as Map<*, *>)["profile_image"].toString()
}