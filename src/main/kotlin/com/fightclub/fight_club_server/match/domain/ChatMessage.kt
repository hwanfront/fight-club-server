package com.fightclub.fight_club_server.match.domain

import com.fightclub.fight_club_server.user.domain.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chat_message")
class ChatMessage (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val match: Match,

    @ManyToOne(fetch = FetchType.LAZY)
    val sender: User,

    val content: String,

    @Enumerated(EnumType.STRING)
    val messageType: ChatMessageType,

    val isRead: Boolean,

    val sentAt: LocalDateTime = LocalDateTime.now()
)