package com.fightclub.fight_club_server.common.exception

import com.fightclub.fight_club_server.common.constants.CommonErrorCode


class UnauthorizedException : BaseException(CommonErrorCode.UNAUTHORIZED)