package com.fightclub.fight_club_server.match.repository

import com.fightclub.fight_club_server.match.domain.MatchReadStatus
import com.fightclub.fight_club_server.match.domain.MatchReadStatusId
import org.springframework.data.jpa.repository.JpaRepository

interface MatchReadStatusRepository: JpaRepository<MatchReadStatus, MatchReadStatusId> {
}