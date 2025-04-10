package com.fightclub.fight_club_server.matchProposal.constants

object MatchProposalConstants {
    object GetReceivedProposalList {
        const val STATUS_CODE = "200"
        const val CODE = "MPS001"
        const val MESSAGE = "받은 매치 요청 목록 조회를 성공하였습니다."
    }

    object GetSentProposalList {
        const val STATUS_CODE = "200"
        const val CODE = "MPS002"
        const val MESSAGE = "보낸 매치 요청 목록 조회를 성공하였습니다."
    }

    object AcceptProposalSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MPS003"
        const val MESSAGE = "매치 요청을 수락하였습니다."
    }

    object RejectProposalSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MPS004"
        const val MESSAGE = "매치 요청을 거절하였습니다."
    }

    object CancelMyProposalSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "MPS005"
        const val MESSAGE = "매치 요청을 취소하였습니다."
    }

    object MatchProposalNotFound {
        const val STATUS_CODE = "404"
        const val CODE = "MPE001"
        const val MESSAGE = "매치 요청을 찾을 수 없습니다."
    }

    object UserIsNotReceiver {
        const val STATUS_CODE = "403"
        const val CODE = "MPE002"
        const val MESSAGE = "해당 매치 요청의 수신자가 아닙니다."

    }
}
