package com.fightclub.fight_club_server.matchingWait.constants

object MatchingWaitConstants {
    object MyMatchingWaitSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MWS001"
        const val MESSAGE = "내 매칭 대기 정보를 불러왔습니다."
    }

    object CreateMatchingWaitSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MWS002"
        const val MESSAGE = "매칭 대기를 등록했습니다."
    }

    object DeleteMatchingWaitSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MWS003"
        const val MESSAGE = "매칭 대기를 삭제했습니다."
    }

    object UpdateMatchingWaitSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MWS004"
        const val MESSAGE = "매칭 대기를 수정했습니다."
    }

    object RandomCandidateSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MWS005"
        const val MESSAGE = "체급에 맞는 매칭 대기 정보를 불러왔습니다."
    }

    object CreateSendMatchProposalSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MWS006"
        const val MESSAGE = "매치 신청을 보냈습니다."
    }


    object MatchingWaitNotFound {
        const val STATUS_CODE = "404"
        const val CODE = "MWE001"
        const val MESSAGE = "매칭 대기 정보가 존재하지 않습니다."
    }

    object MatchingWaitAlreadyExists {
        const val STATUS_CODE = "409"
        const val CODE = "MWE002"
        const val MESSAGE = "이미 매칭 대기 중입니다."
    }
}
