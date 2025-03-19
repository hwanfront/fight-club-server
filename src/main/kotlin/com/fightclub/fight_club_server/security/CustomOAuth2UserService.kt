package com.fightclub.fight_club_server.security

import com.fightclub.fight_club_server.user.domain.AuthProvider
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserStatus
import com.fightclub.fight_club_server.user.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(private val userRepository: UserRepository): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId
        val attributes = oAuth2User.attributes

        val (provider, providerId) = extractProviderAndId(registrationId, attributes)
        val existingUser = userRepository.findByProviderAndProviderId(provider, providerId)

        if (existingUser != null) {
            return oAuth2User
        }
        val email = extractEmail(registrationId, attributes)

        val newUser = User(
            email = email,
            providerId = providerId,
            provider = provider,
            status = UserStatus.WAITING
        )
        userRepository.save(newUser)

        return oAuth2User
    }

    private fun extractProviderAndId(registrationId: String, attributes: Map<String, Any>): Pair<AuthProvider, String> {
        return when (registrationId) {
            "google" -> AuthProvider.GOOGLE to attributes["sub"].toString()
            "kakao" -> AuthProvider.KAKAO to attributes["id"].toString()
            "naver" -> {
                val response = attributes["response"] as Map<*, *>
                AuthProvider.NAVER to response["id"].toString()
            }
            else -> AuthProvider.NONE to ""
        }
    }

    private fun extractEmail(registrationId: String, attributes: Map<String, Any>): String {
        return when (registrationId) {
            "google" -> attributes["email"].toString()
            "kakao" -> (attributes["kakao_account"] as Map<*, *>)["email"].toString()
            "naver" -> (attributes["response"] as Map<*, *>)["email"].toString()
            else -> "unknown"
        }
    }
}
