package com.fightclub.fight_club_server.auth.service

import com.fightclub.fight_club_server.auth.dto.*
import com.fightclub.fight_club_server.auth.exception.InvalidPasswordException
import com.fightclub.fight_club_server.auth.exception.InvalidRefreshTokenException
import com.fightclub.fight_club_server.auth.exception.RefreshTokenNotFoundException
import com.fightclub.fight_club_server.security.jwt.TokenProvider
import com.fightclub.fight_club_server.security.jwt.domain.RefreshToken
import com.fightclub.fight_club_server.security.jwt.repository.RefreshTokenRepository
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserStatus
import com.fightclub.fight_club_server.user.exception.DeletedUserException
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import com.fightclub.fight_club_server.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    @Transactional
    fun login(loginRequest: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(loginRequest.email)
            ?: throw UserNotFoundException()

        if (user.status == UserStatus.DELETED) {
            throw DeletedUserException()
        }

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

    fun logout(user: User, logoutRequest: LogoutRequest) {
        val refreshToken = refreshTokenRepository.findByUserId(user.id!!)
            ?: throw RefreshTokenNotFoundException()

        if(refreshToken.tokenValue != logoutRequest.refreshToken) {
            throw InvalidRefreshTokenException()
        }

        refreshTokenRepository.delete(refreshToken)
    }

    fun refreshToken(refreshRequest: RefreshRequest): RefreshResponse {
        val refreshToken = refreshRequest.refreshToken
            ?: throw InvalidRefreshTokenException()

        if(!tokenProvider.validateToken(refreshToken)) {
            throw InvalidRefreshTokenException()
        }

        val userId = tokenProvider.getUserIdFromToken(refreshToken)
        val storedRefreshToken = refreshTokenRepository.findByUserId(userId)
            ?: throw RefreshTokenNotFoundException()

        if (storedRefreshToken.tokenValue != refreshToken) {
            throw InvalidRefreshTokenException()
        }

        val newAccessToken = tokenProvider.generateAccessToken(userId)
        val newRefreshToken = tokenProvider.generateRefreshToken(userId)

        storedRefreshToken.tokenValue = newRefreshToken
        refreshTokenRepository.save(storedRefreshToken)

        return RefreshResponse(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }
}