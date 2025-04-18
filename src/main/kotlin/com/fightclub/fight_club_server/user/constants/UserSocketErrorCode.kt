package com.fightclub.fight_club_server.user.constants

import com.fightclub.fight_club_server.common.constants.SocketResponseCode

enum class UserSocketErrorCode(
    override val code: String,
    override val message: String,
): SocketResponseCode {
    USER_NOT_FOUND(UserConstants.UserNotFound.CODE, UserConstants.UserNotFound.MESSAGE),
}