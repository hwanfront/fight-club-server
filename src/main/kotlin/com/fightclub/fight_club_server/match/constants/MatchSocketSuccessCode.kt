package com.fightclub.fight_club_server.match.constants

import com.fightclub.fight_club_server.common.constants.SocketResponseCode

enum class MatchSocketSuccessCode(
    override val code: String,
    override val message: String,
): SocketResponseCode {
    MATCH_READY_UPDATED_SUCCESS(MatchConstants.MatchReadyUpdatedSuccess.CODE, MatchConstants.MatchReadyUpdatedSuccess.MESSAGE),
    DECLINE_MATCH_SUCCESS(MatchConstants.DeclineMatchSuccess.CODE, MatchConstants.DeclineMatchSuccess.MESSAGE),
    NEW_CHAT_MESSAGE_RECEIVED_SUCCESS(MatchConstants.NewChatMessageReceivedSuccess.CODE, MatchConstants.NewChatMessageReceivedSuccess.MESSAGE),
}