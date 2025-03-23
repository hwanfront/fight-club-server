package com.fightclub.fight_club_server.security.exception

import com.fightclub.fight_club_server.common.exception.BaseException
import com.fightclub.fight_club_server.security.constants.SecurityErrorCode


class UnsupportedProviderException : BaseException(SecurityErrorCode.UNSUPPORTED_PROVIDER)