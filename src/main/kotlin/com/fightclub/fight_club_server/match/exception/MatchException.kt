package com.fightclub.fight_club_server.match.exception

import com.fightclub.fight_club_server.common.exception.BaseException
import com.fightclub.fight_club_server.common.exception.SocketBaseException
import com.fightclub.fight_club_server.match.constants.MatchErrorCode
import com.fightclub.fight_club_server.match.constants.MatchSocketErrorCode

class MatchNotFoundException: BaseException(MatchErrorCode.MATCH_NOT_FOUND)
class UserIsNotParticipantException: BaseException(MatchErrorCode.USER_IS_NOT_PARTICIPANT)

class MatchNotFoundSocketException(
    override val socketResponseCode: MatchSocketErrorCode = MatchSocketErrorCode.MATCH_NOT_FOUND
) : SocketBaseException(MatchSocketErrorCode.MATCH_NOT_FOUND)
class UserIsNotParticipantSocketException(
    override val socketResponseCode: MatchSocketErrorCode = MatchSocketErrorCode.USER_IS_NOT_PARTICIPANT
): SocketBaseException(MatchSocketErrorCode.USER_IS_NOT_PARTICIPANT)
class UnnecessaryReadUpdateSocketException(
    override val socketResponseCode: MatchSocketErrorCode = MatchSocketErrorCode.UNNECESSARY_READ_UPDATE
): SocketBaseException(MatchSocketErrorCode.UNNECESSARY_READ_UPDATE)