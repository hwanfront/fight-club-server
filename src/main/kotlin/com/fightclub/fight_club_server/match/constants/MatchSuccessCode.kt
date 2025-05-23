package com.fightclub.fight_club_server.match.constants

import com.fightclub.fight_club_server.common.constants.ResponseCode
import org.springframework.http.HttpStatus

enum class MatchSuccessCode(
    override val status: HttpStatus,
    override val code: String,
    override val message: String
): ResponseCode {
    GET_MY_MATCH_LIST_SUCCESS(HttpStatus.OK, MatchConstants.GetMyMatchListSuccess.CODE, MatchConstants.GetMyMatchListSuccess.MESSAGE),
    GET_MATCH_INFO_SUCCESS(HttpStatus.OK, MatchConstants.GetMatchInfoSuccess.CODE, MatchConstants.GetMatchInfoSuccess.MESSAGE),
    START_MATCH_STREAMING_SUCCESS(HttpStatus.OK, MatchConstants.StartMatchStreamingSuccess.CODE, MatchConstants.StartMatchStreamingSuccess.MESSAGE),
    PAUSE_MATCH_STREAMING_SUCCESS(HttpStatus.OK, MatchConstants.PauseMatchStreamingSuccess.CODE, MatchConstants.PauseMatchStreamingSuccess.MESSAGE),
    RESUME_MATCH_STREAMING_SUCCESS(HttpStatus.OK, MatchConstants.ResumeMatchStreamingSuccess.CODE, MatchConstants.ResumeMatchStreamingSuccess.MESSAGE),
    END_MATCH_STREAMING_SUCCESS(HttpStatus.OK, MatchConstants.EndMatchStreamingSuccess.CODE, MatchConstants.EndMatchStreamingSuccess.MESSAGE),

    GET_CHAT_MESSAGE_LIST_SUCCESS(HttpStatus.OK, MatchConstants.GetChatMessageListSuccess.CODE, MatchConstants.GetChatMessageListSuccess.MESSAGE),
}