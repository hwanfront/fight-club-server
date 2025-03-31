package com.fightclub.fight_club_server.user.controller

import com.fightclub.fight_club_server.user.constants.UserSuccessCode
import com.fightclub.fight_club_server.common.dto.BaseResponse
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

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun myInfo(@AuthenticationPrincipal user: User) = BaseResponse.success(UserSuccessCode.MY_INFO_SUCCESS, userService.myInfo(user))

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    fun userInfo(@PathVariable userId: Long) = BaseResponse.success(UserSuccessCode.USER_INFO_SUCCESS, userService.userInfo(userId))

    @PostMapping("/signup")
    @PreAuthorize("isAnonymous()")
    fun signup(@Valid @RequestBody signupRequest: SignupRequest) = BaseResponse.success(UserSuccessCode.SIGNUP_SUCCESS, userService.signup(signupRequest))

    @PatchMapping("/complete")
    @PreAuthorize("isAuthenticated()")
    fun oAuth2Signup(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody oAuth2SignupRequest: OAuth2SignupRequest
    ) = BaseResponse.success(UserSuccessCode.SIGNUP_SUCCESS, userService.oAuth2Signup(user, oAuth2SignupRequest))

    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    fun deleteUser(
        @AuthenticationPrincipal user: User
    ) = BaseResponse.success(UserSuccessCode.DELETE_SUCCESS, userService.deleteUser(user))
}