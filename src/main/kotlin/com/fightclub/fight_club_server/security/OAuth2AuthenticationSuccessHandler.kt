package com.fightclub.fight_club_server.security

import com.fightclub.fight_club_server.security.jwt.TokenProvider
import com.fightclub.fight_club_server.security.jwt.domain.RefreshToken
import com.fightclub.fight_club_server.security.jwt.repository.RefreshTokenRepository
import com.fightclub.fight_club_server.security.userinfo.OAuth2UserInfoFactory
import com.fightclub.fight_club_server.user.domain.UserStatus
import com.fightclub.fight_club_server.user.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
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
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value("\${app.oauth2.redirect-uri}") // yml 에서 값 주입
    private val redirectUri: String
) : AuthenticationSuccessHandler {

    @Throws(IOException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oAuth2User = authentication.principal as? OAuth2User
            ?: return response.sendRedirect("/error")
        val registrationId = (authentication as? OAuth2AuthenticationToken)?.authorizedClientRegistrationId ?: "none"

        val strategy = OAuth2UserInfoFactory.getStrategy(registrationId)
        val provider = strategy.provider
        val providerId = strategy.extractProviderId(oAuth2User.attributes)

        val user = userRepository.findByProviderAndProviderId(provider, providerId)
            ?.takeIf { it.status != UserStatus.DELETED }
            ?: return response.sendRedirect("/error")
        val userId = user.id!!
        val accessToken = tokenProvider.generateAccessToken(userId)
        val refreshToken = tokenProvider.generateRefreshToken(userId)

        val existRefreshToken = refreshTokenRepository.findByUserId(userId)

        if(existRefreshToken != null) {
            existRefreshToken.tokenValue = refreshToken
            refreshTokenRepository.save(existRefreshToken)
        } else {
            refreshTokenRepository.save(
                RefreshToken(
                userId = userId,
                tokenValue = refreshToken
            )
            )
        }
        response.sendRedirect("$redirectUri?accessToken=$accessToken&refreshToken=$refreshToken")
    }
}