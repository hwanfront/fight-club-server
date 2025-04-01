package com.fightclub.fight_club_server.user.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.user.constants.UserConstants
import com.fightclub.fight_club_server.user.dto.UserInfoResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "내 정보 조회 성공 응답")
data class MyInfoSuccessResponse(
    @Schema(description = "HTTP 상태 코드", example = UserConstants.MyInfoSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = UserConstants.MyInfoSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = UserConstants.MyInfoSuccess.MESSAGE)
    override val message: String,

    @Schema(description = "응답 데이터")
    override val data: UserInfoResponse,

): ApiResult<UserInfoResponse>