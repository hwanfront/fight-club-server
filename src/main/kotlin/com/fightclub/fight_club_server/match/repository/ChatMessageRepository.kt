package com.fightclub.fight_club_server.match.repository

import com.fightclub.fight_club_server.match.domain.ChatMessage
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageRepository: JpaRepository<ChatMessage, Long>