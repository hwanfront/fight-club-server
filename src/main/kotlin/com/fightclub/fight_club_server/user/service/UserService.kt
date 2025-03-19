package com.fightclub.fight_club_server.user.service

import com.fightclub.fight_club_server.common.exception.UnauthorizedException
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.dto.UserInfoResponse
import com.fightclub.fight_club_server.user.dto.toUserInfoResponse
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import com.fightclub.fight_club_server.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
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
}