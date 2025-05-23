package com.fightclub.fight_club_server.match.constants

object MatchConstants {
    object GetMyMatchListSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MS001"
        const val MESSAGE = "내 매치 목록 조회를 성공하였습니다."
    }

    object GetMatchInfoSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MS002"
        const val MESSAGE = "매치 정보 조회를 성공하였습니다."
    }

    object StartMatchStreamingSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MS005"
        const val MESSAGE = "방송 시작"
    }

    object PauseMatchStreamingSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MS006"
        const val MESSAGE = "방송 일시중지"
    }

    object ResumeMatchStreamingSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MS007"
        const val MESSAGE = "방송 일시중지 해제"
    }

    object EndMatchStreamingSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MS008"
        const val MESSAGE = "방송 종류"
    }

    object GetChatMessageListSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MS009"
        const val MESSAGE = "채팅 메시지 조회를 성공하였습니다."
    }


    object MatchNotFound {
        const val STATUS_CODE = "404"
        const val CODE = "ME001"
        const val MESSAGE = "매치를 찾을 수 없습니다."
    }

    object UserIsNotParticipant {
        const val STATUS_CODE = "403"
        const val CODE = "ME002"
        const val MESSAGE = "사용자가 매치 참가자가 아닙니다."
    }

    object UnnecessaryReadUpdate {
        const val CODE = "ME003"
        const val MESSAGE = "이미 모든 메시지를 읽었습니다."
    }


    object MatchReadyUpdatedSuccess {
        const val CODE = "MATCH_READY_UPDATED"
        const val MESSAGE = "사용자 준비 상태가 변경되었습니다."
    }

    object DeclineMatchSuccess {
        const val CODE = "DECLINE_MATCH"
        const val MESSAGE = "매치가 거절되었습니다."
    }

    object NewChatMessageReceivedSuccess {
        const val CODE = "NEW_CHAT_MESSAGE_RECEIVED"
        const val MESSAGE = "채팅 메시지를 수신했습니다."
    }

    object ReadChatMessageSuccess {
        const val CODE = "READ_CHAT_MESSAGE"
        const val MESSAGE = "메시지 읽음 처리를 성공하였습니다."
    }

    object TypingStatusReceivedSuccess {
        const val CODE = "TYPING_STATUS_RECEIVED"
        const val MESSAGE = "상대방의 입력 상태를 확인하였습니다."
    }
}