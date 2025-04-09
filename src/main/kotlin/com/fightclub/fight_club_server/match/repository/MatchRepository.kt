package com.fightclub.fight_club_server.match.repository

import com.fightclub.fight_club_server.match.domain.Match
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRepository: JpaRepository<Match, Long> {}