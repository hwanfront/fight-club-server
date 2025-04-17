package com.fightclub.fight_club_server.match.repository

import com.fightclub.fight_club_server.match.domain.ChatMessage
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageRepository: JpaRepository<ChatMessage, Long> {
    fun findByMatchIdOrderByIdDesc(matchId: Long, pageable: Pageable): List<ChatMessage>
    fun findByMatchIdAndIdLessThanOrderByIdDesc(matchId: Long, id: Long, pageable: Pageable): List<ChatMessage>
}