package com.fightclub.fight_club_server.matchProposal.repository

import com.fightclub.fight_club_server.matchProposal.domain.MatchProposal
import com.fightclub.fight_club_server.user.domain.User
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.util.*

interface MatchProposalRepository: JpaRepository<MatchProposal, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByIdWithLock(id: Long): Optional<MatchProposal>
    fun findAllByReceiver(receiver: User): List<MatchProposal>
    fun findAllBySender(sender: User): List<MatchProposal>
}