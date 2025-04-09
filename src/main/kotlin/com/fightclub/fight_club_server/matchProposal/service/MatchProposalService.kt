package com.fightclub.fight_club_server.matchProposal.service

import com.fightclub.fight_club_server.match.domain.Match
import com.fightclub.fight_club_server.match.repository.MatchRepository
import com.fightclub.fight_club_server.matchProposal.dto.ReceivedMatchProposalResponse
import com.fightclub.fight_club_server.matchProposal.dto.SentMatchProposalResponse
import com.fightclub.fight_club_server.matchProposal.mapper.MatchProposalMapper
import com.fightclub.fight_club_server.matchProposal.repository.MatchProposalRepository
import com.fightclub.fight_club_server.notification.service.NotificationService
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.stereotype.Service

@Service
class MatchProposalService(
    private val matchRepository: MatchRepository,
    private val matchProposalRepository: MatchProposalRepository,
    private val matchProposalMapper: MatchProposalMapper,
    private val notificationService: NotificationService,
) {

    fun getReceivedMatchProposalList(user: User): List<ReceivedMatchProposalResponse> {
        return listOf()
    }

    fun getSentMatchProposalList(user: User): List<SentMatchProposalResponse> {
        return listOf()
    }

    fun acceptProposal(matchProposalId: Long, user: User): Match {
        return Match(
            user1 = TODO(),
            user2 = TODO(),
            status = TODO(),
            matchedAt = TODO(),
        )
    }

    fun rejectProposal(matchProposalId: Long, user: User): Unit {

    }

    fun cancelMyProposal(matchProposalId: Long, user: User): Unit {

    }
}