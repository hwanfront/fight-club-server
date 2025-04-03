package com.fightclub.fight_club_server.user.exception

import com.fightclub.fight_club_server.user.constants.UserErrorCode
import com.fightclub.fight_club_server.common.exception.BaseException

class UserNotFoundException : BaseException(UserErrorCode.USER_NOT_FOUND)
class UserAlreadyExistsException : BaseException(UserErrorCode.USER_ALREADY_EXIST)
class UserNotWaitingStatusException : BaseException(UserErrorCode.USER_NOT_WAITING_STATUS)
class DeletedUserException : BaseException(UserErrorCode.USER_DELETED)