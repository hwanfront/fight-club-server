package com.fightclub.fight_club_server.match.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.match.constants.MatchConstants
import com.fightclub.fight_club_server.match.dto.ChatMessageResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "채팅 메시지 리스트 조회")
data class GetChatMessagesResponse(
    @Schema(description = "HTTP 상태 코드", example = MatchConstants.GetChatMessageListSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = MatchConstants.GetChatMessageListSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = MatchConstants.GetChatMessageListSuccess.MESSAGE)
    override val message: String,

    override val data: List<ChatMessageResponse>
): ApiResult<List<ChatMessageResponse>>
