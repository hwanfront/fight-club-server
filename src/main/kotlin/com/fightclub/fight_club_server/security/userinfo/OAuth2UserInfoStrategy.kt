package com.fightclub.fight_club_server.security.userinfo

import com.fightclub.fight_club_server.user.domain.AuthProvider

interface OAuth2UserInfoStrategy {
    val provider: AuthProvider
    fun extractProviderId(attributes: Map<String, Any>): String
    fun extractEmail(attributes: Map<String, Any>): String
    fun extractProfileUrl(attributes: Map<String, Any>): String
}