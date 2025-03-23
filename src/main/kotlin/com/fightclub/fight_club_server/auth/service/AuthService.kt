package com.fightclub.fight_club_server.auth.service

import com.fightclub.fight_club_server.auth.dto.LoginRequest
import com.fightclub.fight_club_server.auth.dto.LoginResponse
import com.fightclub.fight_club_server.auth.exception.InvalidPasswordException
import com.fightclub.fight_club_server.jwt.TokenProvider
import com.fightclub.fight_club_server.jwt.domain.RefreshToken
import com.fightclub.fight_club_server.jwt.repository.RefreshTokenRepository
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import com.fightclub.fight_club_server.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    @Transactional
    fun login(@RequestBody loginRequest: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(loginRequest.email)
            ?: throw UserNotFoundException()

        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw InvalidPasswordException()
        }

        val userId = user.id!!
        val refreshToken = refreshTokenRepository.findByUserId(userId)

        val newAccessToken = tokenProvider.generateAccessToken(userId)
        val newRefreshToken = tokenProvider.generateRefreshToken(userId)

        if (refreshToken == null) {
            refreshTokenRepository.save(
                RefreshToken(
                    userId = userId,
                    tokenValue = newRefreshToken
                )
            )
        } else {
            refreshToken.tokenValue = newRefreshToken
            refreshTokenRepository.save(refreshToken)
        }

        return LoginResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

}