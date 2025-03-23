package com.fightclub.fight_club_server.security.userinfo

import com.fightclub.fight_club_server.security.exception.UnsupportedProviderException

object OAuth2UserInfoFactory {
    fun getStrategy(registrationId: String): OAuth2UserInfoStrategy {
        return when (registrationId) {
            "google" -> GoogleUserInfo()
            "kakao" -> KakaoUserInfo()
            "naver" -> NaverUserInfo()
            else -> throw UnsupportedProviderException()
        }
    }
}