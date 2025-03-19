package com.fightclub.fight_club_server.user.service

import com.fightclub.fight_club_server.common.exception.UnauthorizedException
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.dto.SignupRequest
import com.fightclub.fight_club_server.user.dto.UserInfoResponse
import com.fightclub.fight_club_server.user.dto.toUserInfoResponse
import com.fightclub.fight_club_server.user.exception.UserAlreadyExistsException
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import com.fightclub.fight_club_server.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    fun myInfo(): UserInfoResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            throw UnauthorizedException()
        }

        val user = authentication.principal as? User ?: throw UserNotFoundException()

        return user.toUserInfoResponse()
    }

    fun userInfo(userId: Long): UserInfoResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            throw UnauthorizedException()
        }

        val user = userRepository.findById(userId).orElseThrow{ UserNotFoundException() }

        return user.toUserInfoResponse()
    }

    fun signup(signupRequest: SignupRequest) {
        userRepository.findByEmail(signupRequest.email)?.let {
            throw UserAlreadyExistsException()
        }

        val encodedPassword = bCryptPasswordEncoder.encode(signupRequest.password)

        userRepository.save(User(
            email = signupRequest.email,
            password = encodedPassword,
            nickname = signupRequest.nickname,
            username = signupRequest.username
        ))
    }
}