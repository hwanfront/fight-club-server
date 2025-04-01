package com.fightclub.fight_club_server.user.dto.docs

import com.fightclub.fight_club_server.common.dto.ApiResult
import com.fightclub.fight_club_server.user.constants.UserConstants
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원탈퇴 성공 응답")
data class DeleteUserSuccessResponse(
    @Schema(description = "HTTP 상태 코드", example = UserConstants.DeleteUserSuccess.STATUS_CODE)
    override val status: Int,

    @Schema(description = "응답 코드", example = UserConstants.DeleteUserSuccess.CODE)
    override val code: String,

    @Schema(description = "응답 메시지", example = UserConstants.DeleteUserSuccess.MESSAGE)
    override val message: String,

    @Schema(description = "응답 데이터")
    override val data: Nothing,

): ApiResult<Nothing>