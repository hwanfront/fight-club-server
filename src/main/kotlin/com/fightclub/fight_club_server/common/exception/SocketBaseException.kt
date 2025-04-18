package com.fightclub.fight_club_server.common.exception

import com.fightclub.fight_club_server.common.constants.SocketResponseCode

open class SocketBaseException(
    override val socketResponseCode: SocketResponseCode
) : RuntimeException(socketResponseCode.message), SocketCodeException