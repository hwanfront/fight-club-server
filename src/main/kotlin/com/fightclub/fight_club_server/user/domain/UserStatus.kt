package com.fightclub.fight_club_server.user.domain

enum class UserStatus {
    WAITING, // 소셜 인증 상태 (회원가입 x)
    REGISTERED // 회원가입 완료
}
