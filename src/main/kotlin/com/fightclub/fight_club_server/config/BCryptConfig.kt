package com.fightclub.fight_club_server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class BCryptConfig {
    @Bean
    fun bCryptPasswordEncoder() = BCryptPasswordEncoder()
}