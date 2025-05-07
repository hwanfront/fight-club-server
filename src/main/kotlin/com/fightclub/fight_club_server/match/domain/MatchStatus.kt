package com.fightclub.fight_club_server.match.domain

enum class MatchStatus {
    CHATTING,           // 채팅방에서 대화 중
    READY_TO_STREAM,    // 방송 준비 완료
    STREAMING,          // 방송 중
    PAUSED,             // 방송 일시 중지
    ENDED,              // 매치 종료
    DECLINED            // 거절된 매치
}