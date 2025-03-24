package com.fightclub.fight_club_server.matchingWait.service

import com.fightclub.fight_club_server.common.exception.UnauthorizedException
import com.fightclub.fight_club_server.matchingWait.domain.MatchingWait
import com.fightclub.fight_club_server.matchingWait.dto.MatchingWaitRequest
import com.fightclub.fight_club_server.matchingWait.dto.MatchingWaitResponse
import com.fightclub.fight_club_server.matchingWait.exception.MatchingWaitAlreadyExistsException
import com.fightclub.fight_club_server.matchingWait.exception.MatchingWaitNotFoundException
import com.fightclub.fight_club_server.matchingWait.repository.MatchingWaitRepository
import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class MatchingWaitService(
    val matchingWaitRepository: MatchingWaitRepository
) {
    fun getMyMatchingWait(): MatchingWaitResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            throw UnauthorizedException()
        }

        val user = authentication.principal as? User ?: throw UserNotFoundException()
        val matchingWait = matchingWaitRepository.findByUserId(user.id!!)
            ?: throw MatchingWaitNotFoundException()

        return MatchingWaitResponse(
            weight = matchingWait.weight,
            weightClass = matchingWait.weightClass,
            createdAt = matchingWait.createdAt,
        )
    }

    fun enrollMatchingWait(request: MatchingWaitRequest): MatchingWaitResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            throw UnauthorizedException()
        }

        val user = authentication.principal as? User ?: throw UserNotFoundException()
        val userId = user.id!!

        if (matchingWaitRepository.existsByUserId(userId)) {
            throw MatchingWaitAlreadyExistsException()
        }

        val matchingWait = MatchingWait(
            userId = userId,
            weight = request.weight,
            weightClass = WeightClass.fromWeight(request.weight),
        )

        matchingWaitRepository.save(matchingWait)

        return MatchingWaitResponse(
            weight = matchingWait.weight,
            weightClass = matchingWait.weightClass,
            createdAt = matchingWait.createdAt,
        )
    }

    fun cancelMatchingWait() {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            throw UnauthorizedException()
        }

        val user = authentication.principal as? User ?: throw UserNotFoundException()
        val matchingWait = matchingWaitRepository.findByUserId(user.id!!)
            ?: throw MatchingWaitNotFoundException()

        matchingWaitRepository.delete(matchingWait)
    }

    fun updateMatchingWait(request: MatchingWaitRequest): MatchingWaitResponse {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null || !authentication.isAuthenticated) {
            throw UnauthorizedException()
        }

        val user = authentication.principal as? User ?: throw UserNotFoundException()
        val matchingWait = matchingWaitRepository.findByUserId(user.id!!)
            ?: throw MatchingWaitNotFoundException()

        matchingWait.updateMatchingWait(
            weight = request.weight,
            weightClass = WeightClass.fromWeight(request.weight),
        )

        matchingWaitRepository.save(matchingWait)

        return MatchingWaitResponse(
            weight = matchingWait.weight,
            weightClass = matchingWait.weightClass,
            createdAt = matchingWait.createdAt,
        )
    }
}