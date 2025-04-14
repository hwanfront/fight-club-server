package com.fightclub.fight_club_server.match.repository

import com.fightclub.fight_club_server.match.domain.MatchMessage
import org.springframework.data.jpa.repository.JpaRepository

interface MatchMessageRepository: JpaRepository<MatchMessage, Long>