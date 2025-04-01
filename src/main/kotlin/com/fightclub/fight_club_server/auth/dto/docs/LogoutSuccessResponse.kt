package com.fightclub.fight_club_server.auth.dto.docs

import com.fightclub.fight_club_server.auth.constants.AuthConstants
import com.fightclub.fight_club_server.common.dto.ApiResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "로그아웃 성공 응답")
data class LogoutSuccessResponse(
    @Schema(description = "HTTP 상태 코드", example = AuthConstants.LogoutSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = AuthConstants.LogoutSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = AuthConstants.LogoutSuccess.MESSAGE)
    override val message: String,

    @Schema(description = "응답 데이터")
    override val data: Nothing
): ApiResult<Nothing>
