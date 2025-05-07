package com.fightclub.fight_club_server.security.userinfo

import com.fightclub.fight_club_server.user.domain.AuthProvider

class KakaoUserInfo : OAuth2UserInfoStrategy {
    override val provider = AuthProvider.KAKAO
    override fun extractProviderId(attributes: Map<String, Any>) = attributes["id"].toString()
    override fun extractEmail(attributes: Map<String, Any>) =
        (attributes["kakao_account"] as Map<*, *>)["email"].toString()
}