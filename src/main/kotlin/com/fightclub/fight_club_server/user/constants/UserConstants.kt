package com.fightclub.fight_club_server.user.constants

object UserConstants {
    object MyInfoSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "US001"
        const val MESSAGE = "내 정보 조회 성공"
    }

    object UserInfoSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "US002"
        const val MESSAGE = "회원 정보 조회 성공"
    }

    object SignupSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "US003"
        const val MESSAGE = "회원가입 성공"
    }

    object DeleteUserSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "US004"
        const val MESSAGE = "회원탈퇴 성공"
    }


    object UserNotFound {
        const val STATUS_CODE = "404"
        const val CODE = "UE001"
        const val MESSAGE = "유저가 존재하지 않습니다."
    }
    
    object UserAlreadyExist {
        const val STATUS_CODE = "409"
        const val CODE = "UE002"
        const val MESSAGE = "이미 가입된 사용자입니다."
    }

    object UserNotWaitingStatus {
        const val STATUS_CODE = "403"
        const val CODE = "UE003"
        const val MESSAGE = "소셜 인증 후 추가 정보 입력 대기 상태가 아닙니다."
    }

    object UserDeleted {
        const val STATUS_CODE = "404"
        const val CODE = "UE004"
        const val MESSAGE = "탈퇴한 유저입니다."
    }
}