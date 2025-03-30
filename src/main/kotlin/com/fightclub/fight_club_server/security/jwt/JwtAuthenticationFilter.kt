package com.fightclub.fight_club_server.security.jwt

import com.fightclub.fight_club_server.user.domain.UserStatus
import com.fightclub.fight_club_server.user.exception.UserDeletedException
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
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = resolveToken(request)
            if (!token.isNullOrBlank() && tokenProvider.validateToken(token)) {
                val userId = tokenProvider.getUserIdFromToken(token)
                val user = userRepository.findById(userId)
                    .orElseThrow{ UserNotFoundException() }

                if (user.status == UserStatus.DELETED) {
                    throw UserDeletedException()
                }

                val authToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)

                SecurityContextHolder.getContext().authentication = authToken
            }

        } catch (e: Exception) {
            println(e.message)
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearer = request.getHeader("Authorization") ?: return null
        return if (bearer.startsWith("Bearer ")) bearer.substring(7) else null
    }
}