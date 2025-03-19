package com.fightclub.fight_club_server.user.exception

import com.fightclub.fight_club_server.auth.constants.UserErrorCode
import com.fightclub.fight_club_server.common.exception.BaseException

class UserNotFoundException : BaseException(UserErrorCode.USER_NOT_FOUND)