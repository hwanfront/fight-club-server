package com.fightclub.fight_club_server.matchProposal.repository

import com.fightclub.fight_club_server.matchProposal.domain.MatchProposal
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface MatchProposalRepository: JpaRepository<MatchProposal, Long> {
    fun findAllByReceiver(receiver: User): List<MatchProposal>
    fun findAllBySender(sender: User): List<MatchProposal>
}