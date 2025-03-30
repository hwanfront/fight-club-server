package com.fightclub.fight_club_server.user.service

import com.fightclub.fight_club_server.security.jwt.repository.RefreshTokenRepository
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserStatus
import com.fightclub.fight_club_server.user.dto.*
import com.fightclub.fight_club_server.user.exception.UserAlreadyExistsException
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import com.fightclub.fight_club_server.user.exception.UserNotWaitingStatusException
import com.fightclub.fight_club_server.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    fun myInfo(user: User): UserInfoResponse {
        return user.toUserInfoResponse()
    }

    fun userInfo(userId: Long): UserInfoResponse {
        val user = userRepository.findById(userId).orElseThrow{ UserNotFoundException() }

        return user.toUserInfoResponse()
    }

    fun signup(signupRequest: SignupRequest) {
        userRepository.findByEmail(signupRequest.email)?.let {
            throw UserAlreadyExistsException()
        }

        val encodedPassword = bCryptPasswordEncoder.encode(signupRequest.password)
        val user = signupRequest.toUser(encodedPassword)

        userRepository.save(user)
    }

    fun oAuth2Signup(user: User, oAuth2SignupRequest: OAuth2SignupRequest) {
        if (user.status != UserStatus.WAITING) {
            throw UserNotWaitingStatusException()
        }

        user.updateProfile(
            nickname = oAuth2SignupRequest.nickname,
            username = oAuth2SignupRequest.username,
        )

        userRepository.save(user)
    }

    @Transactional
    fun deleteUser(user: User) {
        user.deleteUser()
        userRepository.save(user)

        refreshTokenRepository.deleteByUserId(user.id!!)
    }
}