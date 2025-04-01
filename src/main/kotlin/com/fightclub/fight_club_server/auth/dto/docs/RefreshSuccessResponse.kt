package com.fightclub.fight_club_server.auth.dto.docs

import com.fightclub.fight_club_server.auth.constants.AuthConstants
import com.fightclub.fight_club_server.auth.dto.RefreshResponse
import com.fightclub.fight_club_server.common.dto.ApiResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "토큰 갱신 성공 응답")
data class RefreshSuccessResponse (

    @Schema(description = "HTTP 상태 코드", example = AuthConstants.TokenRefreshSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = AuthConstants.TokenRefreshSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = AuthConstants.TokenRefreshSuccess.MESSAGE)
    override val message: String,

    @Schema(description = "응답 데이터")
    override val data: RefreshResponse
): ApiResult<RefreshResponse>