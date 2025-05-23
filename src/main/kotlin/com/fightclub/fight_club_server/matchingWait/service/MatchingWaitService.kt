package com.fightclub.fight_club_server.matchingWait.service

import com.fightclub.fight_club_server.matchProposal.repository.MatchProposalRepository
import com.fightclub.fight_club_server.matchingWait.domain.MatchingWait
import com.fightclub.fight_club_server.matchingWait.dto.*
import com.fightclub.fight_club_server.matchingWait.exception.MatchingWaitAlreadyExistsException
import com.fightclub.fight_club_server.matchingWait.exception.MatchingWaitNotFoundException
import com.fightclub.fight_club_server.matchingWait.mapper.MatchingWaitMapper
import com.fightclub.fight_club_server.matchingWait.repository.MatchingWaitRepository
import com.fightclub.fight_club_server.meta.enums.WeightClass
import com.fightclub.fight_club_server.notification.service.NotificationService
import com.fightclub.fight_club_server.user.domain.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class MatchingWaitService(
    private val matchingWaitRepository: MatchingWaitRepository,
    private val matchProposalRepository: MatchProposalRepository,
    private val notificationService: NotificationService,
    private val matchingWaitMapper: MatchingWaitMapper
) {
    fun getMyMatchingWait(user: User): MatchingWaitResponse {
        val matchingWait = matchingWaitRepository.findByUser(user)
            ?: throw MatchingWaitNotFoundException()

        return matchingWaitMapper.toResponse(matchingWait)
    }

    fun createMatchingWait(user: User, request: MatchingWaitRequest): MatchingWaitResponse {
        if (matchingWaitRepository.existsByUserId(user.id!!)) {
            throw MatchingWaitAlreadyExistsException()
        }

        val matchingWait = MatchingWait(
            user = user,
            weight = request.weight,
            weightClass = WeightClass.fromWeight(request.weight),
        )

        matchingWaitRepository.save(matchingWait)

        return matchingWaitMapper.toResponse(matchingWait)
    }

    fun cancelMatchingWait(user: User) {
        val matchingWait = matchingWaitRepository.findByUser(user)
            ?: throw MatchingWaitNotFoundException()

        matchingWaitRepository.delete(matchingWait)
    }

    fun updateMatchingWait(user: User, request: MatchingWaitRequest): MatchingWaitResponse {
        val matchingWait = matchingWaitRepository.findByUser(user)
            ?: throw MatchingWaitNotFoundException()

        matchingWait.updateMatchingWait(
            weight = request.weight,
            weightClass = WeightClass.fromWeight(request.weight),
        )

        matchingWaitRepository.save(matchingWait)

        return matchingWaitMapper.toResponse(matchingWait)
    }

    fun getCandidateList(user: User): List<MatchingCandidateResponse> {
        val myWait = matchingWaitRepository.findByUser(user)
            ?: throw MatchingWaitNotFoundException()

        val candidateList =  matchingWaitRepository.findCandidateListByWeightClassRandom(
            weightClass = myWait.weightClass.name,
            userId = user.id!!,
            limit = 10
        )

        return candidateList.map { matchingWaitMapper.toCandidateResponse(it) }
    }

    @Transactional
    fun sendMatchProposal(user: User, request: SendMatchRequest) {
        val senderWait = matchingWaitRepository.findByUserId(user.id!!) ?: throw MatchingWaitNotFoundException()
        val receiverWait = matchingWaitRepository.findByUserId(request.receiverId) ?: throw MatchingWaitNotFoundException()
        val matchProposal = matchingWaitMapper.toMatchProposal(senderWait, receiverWait)

        val savedMatchProposal = matchProposalRepository.save(matchProposal)
        notificationService.notifyMatchProposal(savedMatchProposal)
    }
}