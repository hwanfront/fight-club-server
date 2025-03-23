package com.fightclub.fight_club_server.auth.exception

import com.fightclub.fight_club_server.auth.constants.AuthErrorCode
import com.fightclub.fight_club_server.common.exception.BaseException

class InvalidPasswordException : BaseException(AuthErrorCode.INVALID_PASSWORD)
class InvalidRefreshTokenException : BaseException(AuthErrorCode.INVALID_REFRESH_TOKEN)
class RefreshTokenNotFoundException : BaseException(AuthErrorCode.REFRESH_TOKEN_NOT_FOUND)
