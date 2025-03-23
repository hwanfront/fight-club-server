package com.fightclub.fight_club_server.auth.controller

import com.fightclub.fight_club_server.auth.constants.AuthSuccessCode
import com.fightclub.fight_club_server.auth.dto.LoginRequest
import com.fightclub.fight_club_server.auth.service.AuthService
import com.fightclub.fight_club_server.common.dto.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest) = ApiResponse.success(AuthSuccessCode.LOGIN_SUCCESS, authService.login(loginRequest))
}