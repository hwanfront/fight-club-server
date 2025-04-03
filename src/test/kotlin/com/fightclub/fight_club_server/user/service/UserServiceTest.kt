package com.fightclub.fight_club_server.user.service

import com.fightclub.fight_club_server.auth.service.AuthService
import com.fightclub.fight_club_server.security.jwt.repository.RefreshTokenRepository
import com.fightclub.fight_club_server.user.domain.AuthProvider
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserRole
import com.fightclub.fight_club_server.user.domain.UserStatus
import com.fightclub.fight_club_server.user.dto.OAuth2SignupRequest
import com.fightclub.fight_club_server.user.dto.SignupRequest
import com.fightclub.fight_club_server.user.dto.UserInfoResponse
import com.fightclub.fight_club_server.user.exception.UserAlreadyExistsException
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import com.fightclub.fight_club_server.user.exception.UserNotWaitingStatusException
import com.fightclub.fight_club_server.user.mapper.UserMapper
import com.fightclub.fight_club_server.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @Mock lateinit var userRepository: UserRepository
    @Mock lateinit var refreshTokenRepository: RefreshTokenRepository
    @Mock lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder
    @Mock lateinit var userMapper: UserMapper

    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userService = UserService(
            userRepository = userRepository,
            refreshTokenRepository = refreshTokenRepository,
            bCryptPasswordEncoder = bCryptPasswordEncoder,
            userMapper = userMapper
        )
    }

    @Test
    fun `# userInfo success`() {
        // given
        val userId = 1L
        val email = "test@example.com"
        val user = User(
            id = userId,
            email = email,
            nickname = "nickname",
            username = "username",
            providerId = null,
            provider = AuthProvider.NONE,
            status = UserStatus.REGISTERED,
        )
        val response = UserInfoResponse(
            email = email,
            username = "nickname",
            nickname = "nickname",
            providerId = null,
            provider = AuthProvider.NONE,
            role = UserRole.ROLE_USER
        )

        given(userRepository.findById(userId)).willReturn(Optional.of(user))
        given(userMapper.toUserInfoResponse(user)).willReturn(response)

        // when
        val result = userService.userInfo(userId)

        // then
        assertThat(result.email).isEqualTo(response.email)
    }

    @Test
    fun `# userInfo failed - 유저를 찾을 수 없음 UserNotFoundException`() {
        // given
        val userId = 1L

        given(userRepository.findById(userId)).willReturn(Optional.empty())

        // when
        // then
        assertThrows(UserNotFoundException::class.java) {
            userService.userInfo(userId)
        }
    }

    @Test
    fun `# signup success`() {
        // given
        val email = "test@gmail.com"
        val nickname = "nickname"
        val username = "username"
        val password = "pw"
        val encodedPassword = "encodedPassword"

        val signupRequest = SignupRequest(
            email = email,
            nickname = nickname,
            username = username,
            password = password
        )

        val user = User(
            id = 1L,
            email = email,
            password = password,
            username = username,
            nickname = nickname,
            providerId = null,
            provider = AuthProvider.NONE,
            status = UserStatus.REGISTERED,
            role = UserRole.ROLE_USER
        )

        given(userRepository.findByEmail(email)).willReturn(null)
        given(bCryptPasswordEncoder.encode(password)).willReturn("encodedPassword")
        given(userMapper.fromSignupRequest(signupRequest, encodedPassword)).willReturn(user)
        given(userRepository.save(user)).willReturn(user)

        // when
        userService.signup(signupRequest)

        // then
        verify(userRepository).save(user)
    }

    @Test
    fun `# signup failed - 중복 이메일 UserAlreadyExistsException`() {
        // given
        val email = "test@gmail.com"

        val signupRequest = SignupRequest(
            email = email,
            nickname = "nickname",
            username = "username",
            password = "pw"
        )
        val existingUser = mock(User::class.java)

        given(userRepository.findByEmail(email)).willReturn(existingUser)

        // when
        // then
        assertThrows(UserAlreadyExistsException::class.java) {
            userService.signup(signupRequest)
        }

        verify(userRepository, never()).save(any())
    }

    @Test
    fun `# oAuth2Signup success`() {
        // given
        val nickname = "nickname"
        val username = "username"

        val user = User(
            id = 1L,
            username = null,
            nickname = null,
            providerId = "provider_id",
            provider = AuthProvider.GOOGLE,
            status = UserStatus.WAITING,
            role = UserRole.ROLE_USER,
        )
        val oAuth2SignupRequest = OAuth2SignupRequest(
            nickname = nickname,
            username = username,
        )

        // when
        userService.oAuth2Signup(user, oAuth2SignupRequest)

        // then
        assertThat(user.nickname).isEqualTo(nickname)
        assertThat(user.username).isEqualTo(username)
        verify(userRepository).save(user)
    }

    @Test
    fun `# oAuth2Signup failed - 유저 상태가 WAITING 이 아님`() {
        // given
        val nickname = "nickname"
        val username = "username"

        val user = User(
            id = 1L,
            username = null,
            nickname = null,
            providerId = "provider_id",
            provider = AuthProvider.GOOGLE,
            status = UserStatus.REGISTERED,
            role = UserRole.ROLE_USER,
        )
        val oAuth2SignupRequest = OAuth2SignupRequest(
            nickname = nickname,
            username = username,
        )

        // when
        // then
        assertThrows(UserNotWaitingStatusException::class.java) {
            userService.oAuth2Signup(user, oAuth2SignupRequest)
        }

        verify(userRepository, never()).save(any())
    }

    @Test
    fun `# deleteUser success`() {
        // given
        val userId = 1L

        val user = User(
            id = userId,
            email = "test@gmail.com",
            password = "nickname",
            username = "username",
            nickname ="pw",
            providerId = null,
            provider = AuthProvider.NONE,
            status = UserStatus.REGISTERED,
            role = UserRole.ROLE_USER
        )

        // when
        userService.deleteUser(user)

        // then
        assertThat(user.status).isEqualTo(UserStatus.DELETED)
        verify(userRepository).save(user)
        verify(refreshTokenRepository).deleteByUserId(userId)
    }
}