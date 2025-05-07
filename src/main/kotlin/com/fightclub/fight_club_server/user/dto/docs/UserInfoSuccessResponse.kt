package com.fightclub.fight_club_server.user.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.user.constants.UserConstants
import com.fightclub.fight_club_server.user.dto.UserInfoResponse
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원 정보 조회 성공 응답")
data class UserInfoSuccessResponse(
    @Schema(description = "HTTP 상태 코드", example = UserConstants.UserInfoSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = UserConstants.UserInfoSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = UserConstants.UserInfoSuccess.MESSAGE)
    override val message: String,

    @Schema(description = "응답 데이터")
    override val data: UserInfoResponse,

): ApiResult<UserInfoResponse>