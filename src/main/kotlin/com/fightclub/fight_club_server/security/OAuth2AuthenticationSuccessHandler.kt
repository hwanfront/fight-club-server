package com.fightclub.fight_club_server.security

import com.fightclub.fight_club_server.jwt.TokenProvider
import com.fightclub.fight_club_server.jwt.domain.RefreshToken
import com.fightclub.fight_club_server.jwt.repository.RefreshTokenRepository
import com.fightclub.fight_club_server.user.domain.AuthProvider
import com.fightclub.fight_club_server.user.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class OAuth2AuthenticationSuccessHandler(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository
) : AuthenticationSuccessHandler {

    @Throws(IOException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oAuth2User = authentication.principal as? OAuth2User
            ?: return response.sendRedirect("/error")
        val registrationId = getRegistrationIdFromAuth(authentication)
        val providerId = extractProviderId(registrationId, oAuth2User.attributes)

        val provider = when (registrationId) {
            "google" -> AuthProvider.GOOGLE
            "kakao" -> AuthProvider.KAKAO
            "naver" -> AuthProvider.NAVER
            else -> AuthProvider.NONE
        }

        val user = userRepository.findByProviderAndProviderId(provider, providerId)
            ?: return response.sendRedirect("/error")
        val userId = user.id!!
        val accessToken = tokenProvider.generateAccessToken(userId)
        val refreshToken = tokenProvider.generateRefreshToken(userId)

        val existRefreshToken = refreshTokenRepository.findByUserId(userId)

        if(existRefreshToken != null) {
            existRefreshToken.tokenValue = refreshToken
            refreshTokenRepository.save(existRefreshToken)
        } else {
            refreshTokenRepository.save(RefreshToken(
                userId = userId,
                tokenValue = refreshToken
            ))
        }
        response.sendRedirect("http://localhost:5173?accessToken=$accessToken&refreshToken=$refreshToken")
    }

    private fun getRegistrationIdFromAuth(authentication: Authentication): String {
        if (authentication is OAuth2AuthenticationToken) {
            return authentication.authorizedClientRegistrationId
        }
        return "none"
    }

    private fun extractProviderId(registrationId: String, attributes: Map<String, Any>): String {
        return when (registrationId) {
            "google" -> attributes["sub"].toString()
            "kakao" -> attributes["id"].toString()
            "naver" -> {
                val response = attributes["response"] as Map<*, *>
                response["id"].toString()
            }
            else -> ""
        }
    }
}