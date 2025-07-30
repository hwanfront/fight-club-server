package com.fightclub.fight_club_server.auth.controller

import com.fightclub.fight_club_server.auth.constants.AuthConstants
import com.fightclub.fight_club_server.auth.constants.AuthSuccessCode
import com.fightclub.fight_club_server.auth.dto.*
import com.fightclub.fight_club_server.auth.dto.docs.*
import com.fightclub.fight_club_server.auth.service.AuthService
import com.fightclub.fight_club_server.common.dto.BaseResponse
import com.fightclub.fight_club_server.security.jwt.JwtProperties
import com.fightclub.fight_club_server.user.constants.UserConstants
import com.fightclub.fight_club_server.user.domain.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService, private val jwtProperties: JwtProperties) {

    @Operation(
        summary = "로그인",
        responses = [
            ApiResponse(
                responseCode = AuthConstants.LoginSuccess.STATUS_CODE,
                description = "[${AuthConstants.LoginSuccess.CODE}] ${AuthConstants.LoginSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = LoginSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = AuthConstants.InvalidPassword.STATUS_CODE,
                description = "[${AuthConstants.InvalidPassword.CODE}] ${AuthConstants.InvalidPassword.MESSAGE}",
                content = [Content(schema = Schema(implementation = InvalidPasswordResponse::class))]
            ),
            ApiResponse(
                responseCode = UserConstants.UserNotFound.STATUS_CODE,
                description = "[${UserConstants.UserNotFound.CODE}] ${UserConstants.UserNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = UserNotFoundResponse::class))],
            )
        ]
    )
    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    fun login(
        @RequestBody loginRequest: LoginRequest,
        response: HttpServletResponse
    ): ResponseEntity<BaseResponse<LoginResponse>> {
        val loginResponse = authService.login(loginRequest)

        val accessTokenCookie = ResponseCookie.from("accessToken", loginResponse.accessToken)
            .httpOnly(true)
            .secure(false) // only dev
            .path("/")
            .maxAge(jwtProperties.accessTokenValidity)
            .build()

        val refreshTokenCookie = ResponseCookie.from("refreshToken", loginResponse.refreshToken)
            .httpOnly(true)
            .secure(false) // only dev
            .path("/")
            .maxAge(jwtProperties.refreshTokenValidity)
            .build()

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
        return BaseResponse.success(AuthSuccessCode.LOGIN_SUCCESS, null)
    }

    @Operation(
        summary = "로그아웃",
        responses = [
            ApiResponse(
                responseCode = AuthConstants.LogoutSuccess.STATUS_CODE,
                description = "[${AuthConstants.LogoutSuccess.CODE}] ${AuthConstants.LogoutSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = LogoutSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = AuthConstants.RefreshTokenNotFound.STATUS_CODE,
                description = "[${AuthConstants.RefreshTokenNotFound.CODE}] ${AuthConstants.RefreshTokenNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = RefreshTokenNotFoundResponse::class))]
            ),
            ApiResponse(
                responseCode = AuthConstants.InvalidRefreshToken.STATUS_CODE,
                description = "[${AuthConstants.InvalidRefreshToken.CODE}] ${AuthConstants.InvalidRefreshToken.MESSAGE}",
                content = [Content(schema = Schema(implementation = InvalidRefreshTokenResponse::class))]
            )
        ]
    )
    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    fun logout(
        @AuthenticationPrincipal user: User,
        @RequestBody logoutRequest: LogoutRequest
    ) = BaseResponse.success(AuthSuccessCode.LOGOUT_SUCCESS, authService.logout(user, logoutRequest))

    @Operation(
        summary = "토큰 리프레시",
        responses = [
            ApiResponse(
                responseCode = AuthConstants.TokenRefreshSuccess.STATUS_CODE,
                description = "[${AuthConstants.TokenRefreshSuccess.CODE}] ${AuthConstants.TokenRefreshSuccess.MESSAGE}",
                content = [Content(schema = Schema(implementation = RefreshSuccessResponse::class))]
            ),
            ApiResponse(
                responseCode = AuthConstants.RefreshTokenNotFound.STATUS_CODE,
                description = "[${AuthConstants.RefreshTokenNotFound.CODE}] ${AuthConstants.RefreshTokenNotFound.MESSAGE}",
                content = [Content(schema = Schema(implementation = RefreshTokenNotFoundResponse::class))]
            ),
            ApiResponse(
                responseCode = AuthConstants.InvalidRefreshToken.STATUS_CODE,
                description = "[${AuthConstants.InvalidRefreshToken.CODE}] ${AuthConstants.InvalidRefreshToken.MESSAGE}",
                content = [Content(schema = Schema(implementation = InvalidRefreshTokenResponse::class))]
            )
        ]
    )
    @PostMapping("/refresh")
    fun refresh(
        @RequestBody refreshRequest: RefreshRequest
    ) = BaseResponse.success(AuthSuccessCode.TOKEN_REFRESH_SUCCESS, authService.refreshToken(refreshRequest))
}
