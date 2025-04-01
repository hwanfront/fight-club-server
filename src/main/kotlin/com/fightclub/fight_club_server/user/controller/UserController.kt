package com.fightclub.fight_club_server.user.controller

import com.fightclub.fight_club_server.user.constants.UserSuccessCode
import com.fightclub.fight_club_server.common.dto.BaseResponse
import com.fightclub.fight_club_server.user.constants.UserConstants
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.dto.OAuth2SignupRequest
import com.fightclub.fight_club_server.user.dto.SignupRequest
import com.fightclub.fight_club_server.user.dto.docs.*
import com.fightclub.fight_club_server.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @Operation(
        summary = "내 정보 조회",
        responses = [
            ApiResponse(
                responseCode = UserConstants.MyInfoSuccess.STATUS_CODE,
                description = "[${UserConstants.MyInfoSuccess.CODE}] ${UserConstants.MyInfoSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserInfoSuccessResponse::class))]
            ),
        ]
    )
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun myInfo(
        @AuthenticationPrincipal user: User
    )= BaseResponse.success(UserSuccessCode.MY_INFO_SUCCESS, userService.myInfo(user))

    @Operation(
        summary = "회원 정보 조회",
        responses = [
            ApiResponse(
                responseCode = UserConstants.UserInfoSuccess.STATUS_CODE,
                description = "[${UserConstants.UserInfoSuccess.CODE}] ${UserConstants.UserInfoSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserInfoSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = UserConstants.UserNotFound.STATUS_CODE,
                description = "[${UserConstants.UserNotFound.CODE}] ${UserConstants.UserNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserNotFoundResponse::class))]
            )
        ]
    )
    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    fun userInfo(
        @PathVariable userId: Long
    ) = BaseResponse.success(UserSuccessCode.USER_INFO_SUCCESS, userService.userInfo(userId))

    @Operation(
        summary = "회원가입",
        responses = [
            ApiResponse(
                responseCode = UserConstants.SignupSuccess.STATUS_CODE,
                description = "[${UserConstants.SignupSuccess.CODE}] ${UserConstants.SignupSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = SignupSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = UserConstants.UserAlreadyExist.STATUS_CODE,
                description = "[${UserConstants.UserAlreadyExist.CODE}] ${UserConstants.UserAlreadyExist.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserAlreadyExistResponse::class))]
            )
        ]
    )
    @PostMapping("/signup")
    @PreAuthorize("isAnonymous()")
    fun signup(
        @Valid @RequestBody signupRequest: SignupRequest
    ) = BaseResponse.success(UserSuccessCode.SIGNUP_SUCCESS, userService.signup(signupRequest))

    @Operation(
        summary = "OAuth2 로그인 후 추가 정보 입력",
        responses = [
            ApiResponse(
                responseCode = UserConstants.SignupSuccess.STATUS_CODE,
                description = "[${UserConstants.SignupSuccess.CODE}] ${UserConstants.SignupSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = SignupSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = UserConstants.UserNotWaitingStatus.STATUS_CODE,
                description = "[${UserConstants.UserNotWaitingStatus.CODE}] ${UserConstants.UserNotWaitingStatus.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserNotWaitingStatusResponse::class))]
            )
        ]
    )
    @PatchMapping("/complete")
    @PreAuthorize("isAuthenticated()")
    fun oAuth2Signup(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody oAuth2SignupRequest: OAuth2SignupRequest
    ) = BaseResponse.success(UserSuccessCode.SIGNUP_SUCCESS, userService.oAuth2Signup(user, oAuth2SignupRequest))

    @Operation(
        summary = "회원탈퇴",
        responses = [
            ApiResponse(
                responseCode = UserConstants.DeleteUserSuccess.STATUS_CODE,
                description = "[${UserConstants.DeleteUserSuccess.CODE}] ${UserConstants.DeleteUserSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = DeleteUserSuccessResponse::class))]
            ),
        ]
    )
    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    fun deleteUser(
        @AuthenticationPrincipal user: User
    ) = BaseResponse.success(UserSuccessCode.DELETE_USER_SUCCESS, userService.deleteUser(user))
}