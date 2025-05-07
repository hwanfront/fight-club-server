package com.fightclub.fight_club_server.user.repository

import com.fightclub.fight_club_server.user.domain.AuthProvider
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByProviderAndProviderId(provider: AuthProvider, providerId: String): User?
}