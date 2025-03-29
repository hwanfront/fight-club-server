package com.fightclub.fight_club_server.matchProposal.repository

import com.fightclub.fight_club_server.matchProposal.domain.MatchProposal
import org.springframework.data.jpa.repository.JpaRepository

interface MatchProposalRepository: JpaRepository<MatchProposal, Long> {
}