package com.fightclub.fight_club_server.user.controller

import com.fightclub.fight_club_server.auth.constants.UserSuccessCode
import com.fightclub.fight_club_server.common.dto.ApiResponse
import com.fightclub.fight_club_server.user.dto.SignupRequest
import com.fightclub.fight_club_server.user.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    @GetMapping("/me")
    fun myInfo() = ApiResponse.success(UserSuccessCode.MY_INFO_SUCCESS, userService.myInfo())

    @GetMapping("/{userId}")
    fun userInfo(@PathVariable userId: Long) = ApiResponse.success(UserSuccessCode.USER_INFO_SUCCESS, userService.userInfo(userId))

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody signupRequest: SignupRequest) = ApiResponse.success(UserSuccessCode.SIGNUP_SUCCESS, userService.signup(signupRequest))
}