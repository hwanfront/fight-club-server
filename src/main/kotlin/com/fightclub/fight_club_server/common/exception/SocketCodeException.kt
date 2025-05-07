package com.fightclub.fight_club_server.common.exception

import com.fightclub.fight_club_server.common.constants.SocketResponseCode

interface SocketCodeException {
    val socketResponseCode: SocketResponseCode
}