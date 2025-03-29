package com.fightclub.fight_club_server.user.controller

import com.fightclub.fight_club_server.user.constants.UserSuccessCode
import com.fightclub.fight_club_server.common.dto.ApiResponse
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.dto.OAuth2SignupRequest
import com.fightclub.fight_club_server.user.dto.SignupRequest
import com.fightclub.fight_club_server.user.service.UserService
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    fun myInfo(@AuthenticationPrincipal user: User) = ApiResponse.success(UserSuccessCode.MY_INFO_SUCCESS, userService.myInfo(user))

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{userId}")
    fun userInfo(@PathVariable userId: Long) = ApiResponse.success(UserSuccessCode.USER_INFO_SUCCESS, userService.userInfo(userId))

    @PreAuthorize("isAnonymous()")
    @PostMapping("/signup")
    fun signup(@Valid @RequestBody signupRequest: SignupRequest) = ApiResponse.success(UserSuccessCode.SIGNUP_SUCCESS, userService.signup(signupRequest))

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/complete")
    fun oAuth2Signup(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody oAuth2SignupRequest: OAuth2SignupRequest
    ) = ApiResponse.success(UserSuccessCode.SIGNUP_SUCCESS, userService.oAuth2Signup(user, oAuth2SignupRequest))

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete")
    fun deleteUser(
        @AuthenticationPrincipal user: User
    ) = ApiResponse.success(UserSuccessCode.DELETE_SUCCESS, userService.deleteUser(user))
}