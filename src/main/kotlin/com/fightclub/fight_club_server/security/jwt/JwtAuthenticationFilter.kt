package com.fightclub.fight_club_server.security.jwt

import com.fightclub.fight_club_server.user.domain.UserStatus
import com.fightclub.fight_club_server.user.exception.DeletedUserException
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import com.fightclub.fight_club_server.user.repository.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    private val PUBLIC_URL_PATTERNS = listOf(
        "/api/auth/login",
        "/api/auth/refresh",
        "/api/users/signup",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/ws/**",
        "/oauth2/authorization/**",
        "/login/oauth2/code/**"
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestUri = request.requestURI
        val isPublicUrl = PUBLIC_URL_PATTERNS.any { pattern ->
            requestUri.startsWith(pattern.substringBeforeLast("/**")) // 간략화된 매칭
            // 또는 정확히 일치하는지 확인
            // requestUri == pattern || (pattern.endsWith("/**") && requestUri.startsWith(pattern.substringBeforeLast("/**")))
        }


        if (isPublicUrl) {
            filterChain.doFilter(request, response)
            return
        }


        try {
            val token = resolveToken(request)
            if (!token.isNullOrBlank() && tokenProvider.validateToken(token)) {
                val userId = tokenProvider.getUserIdFromToken(token)
                val user = userRepository.findById(userId)
                    .orElseThrow{ UserNotFoundException() }

                if (user.status == UserStatus.DELETED) {
                    throw DeletedUserException()
                }

                val authToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)

                SecurityContextHolder.getContext().authentication = authToken
            }

        } catch (e: Exception) {
            System.err.println("JWT Authentication Filter Error: ${e.message}")
            e.printStackTrace() // 🚨 이 라인을 추가하여 상세 스택 트레이스 확인
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }

        return request.cookies?.firstOrNull { it.name == "accessToken" }?.value
    }
}