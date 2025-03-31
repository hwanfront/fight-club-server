package com.fightclub.fight_club_server.auth.controller

import com.fightclub.fight_club_server.auth.constants.AuthSuccessCode
import com.fightclub.fight_club_server.auth.dto.LoginRequest
import com.fightclub.fight_club_server.auth.dto.LogoutRequest
import com.fightclub.fight_club_server.auth.dto.RefreshRequest
import com.fightclub.fight_club_server.auth.service.AuthService
import com.fightclub.fight_club_server.common.dto.BaseResponse
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ) = BaseResponse.success(AuthSuccessCode.LOGIN_SUCCESS, authService.login(loginRequest))

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    fun logout(
        @AuthenticationPrincipal user: User,
        @RequestBody logoutRequest: LogoutRequest
    ) = BaseResponse.success(AuthSuccessCode.LOGOUT_SUCCESS, authService.logout(user, logoutRequest))

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody refreshRequest: RefreshRequest
    ) = BaseResponse.success(AuthSuccessCode.TOKEN_REFRESH_SUCCESS, authService.refreshToken(refreshRequest))
}