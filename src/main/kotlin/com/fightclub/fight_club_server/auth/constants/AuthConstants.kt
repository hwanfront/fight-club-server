package com.fightclub.fight_club_server.auth.constants

object AuthConstants {
    object LoginSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "AS001"
        const val MESSAGE = "로그인 성공"
    }

    object LogoutSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "AS002"
        const val MESSAGE = "로그아웃 성공"
    }

    object TokenRefreshSuccess {
        const val STATUS_CODE = "200"
        const val CODE = "AS003"
        const val MESSAGE = "토큰 리프레시 성공"
    }

    object InvalidPassword {
        const val STATUS_CODE = "401"
        const val CODE = "AE001"
        const val MESSAGE = "비밀번호가 일치하지 않습니다."
    }

    object InvalidRefreshToken {
        const val STATUS_CODE = "401"
        const val CODE = "AE002"
        const val MESSAGE = "리프레시 토큰이 유효하지 않습니다."
    }

    object RefreshTokenNotFound {
        const val STATUS_CODE = "403"
        const val CODE = "AE003"
        const val MESSAGE = "리프레시 토큰을 찾을 수 없습니다."
    }
}