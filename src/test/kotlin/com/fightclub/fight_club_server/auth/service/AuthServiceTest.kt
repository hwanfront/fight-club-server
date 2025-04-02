package com.fightclub.fight_club_server.auth.service

import com.fightclub.fight_club_server.auth.dto.LoginRequest
import com.fightclub.fight_club_server.auth.exception.InvalidPasswordException
import com.fightclub.fight_club_server.security.jwt.TokenProvider
import com.fightclub.fight_club_server.security.jwt.domain.RefreshToken
import com.fightclub.fight_club_server.security.jwt.repository.RefreshTokenRepository
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserStatus
import com.fightclub.fight_club_server.user.exception.DeletedUserException
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import com.fightclub.fight_club_server.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertThrows

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.argumentCaptor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {
    @Mock lateinit var userRepository: UserRepository
    @Mock lateinit var tokenProvider: TokenProvider
    @Mock lateinit var refreshTokenRepository: RefreshTokenRepository
    @Mock lateinit var passwordEncoder: BCryptPasswordEncoder

    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() {
        authService = AuthService(
            userRepository = userRepository,
            tokenProvider = tokenProvider,
            refreshTokenRepository = refreshTokenRepository,
            passwordEncoder = passwordEncoder,
        )
    }

    @Test
    fun `# login success - 기존 리프레시 토큰이 없는 경우`() {
        // given
        val user = User(id = 1L, email = "test@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val request = LoginRequest(email = "test@gmail.com", password = "raw")

        given(userRepository.findByEmail("test@gmail.com")).willReturn(user)
        given(passwordEncoder.matches("raw", "encoded")).willReturn(true)
        given(refreshTokenRepository.findByUserId(1L)).willReturn(null)
        given(tokenProvider.generateAccessToken(1L)).willReturn("access-token")
        given(tokenProvider.generateRefreshToken(1L)).willReturn("refresh-token")

        // when
        val result = authService.login(request)

        // then
        assertThat(result.accessToken).isEqualTo("access-token")
        assertThat(result.refreshToken).isEqualTo("refresh-token")
        verify(refreshTokenRepository).save(any())
    }

    @Test
    fun `# login success - 기존 리프레시 토큰이 있는 경우`() {
        // given
        val user = User(id = 1L, email = "test@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val request = LoginRequest(email = "test@gmail.com", password = "raw")
        val oldRefreshToken = RefreshToken(id = 1L, userId = 1L, tokenValue = "old-token",)

        given(userRepository.findByEmail("test@gmail.com")).willReturn(user)
        given(passwordEncoder.matches("raw", "encoded")).willReturn(true)
        given(refreshTokenRepository.findByUserId(1L)).willReturn(oldRefreshToken)
        given(tokenProvider.generateAccessToken(1L)).willReturn("access-token")
        given(tokenProvider.generateRefreshToken(1L)).willReturn("new-refresh-token")

        // when
        val result = authService.login(request)

        // then
        // update 가 있는 경우, 객체 검증을 위해 captor 사용
        val captor = argumentCaptor<RefreshToken>()
        verify(refreshTokenRepository).save(captor.capture())

        val savedToken = captor.firstValue
        assertThat(savedToken.tokenValue).isEqualTo("new-refresh-token")
        assertThat(savedToken.userId).isEqualTo(1L)
    }

    @Test
    fun `# login failed - 존재하지 않는 유저 UserNotFoundException`() {
        // given
        val request = LoginRequest(email = "test@gmail.com", password = "raw")

        given(userRepository.findByEmail("test@gmail.com")).willReturn(null)

        // when
        // then
        assertThrows(UserNotFoundException::class.java) {
            authService.login(request)
        }
    }

    @Test
    fun `# login failed - 탈퇴한 유저 DeletedUserException`() {
        // given
        val user = User(id = 1L, email = "test@gmail.com", password = "encoded", status = UserStatus.DELETED)
        val request = LoginRequest(email = "test@gmail.com", password = "raw")

        given(userRepository.findByEmail("test@gmail.com")).willReturn(user)

        // when
        // then
        assertThrows(DeletedUserException::class.java) {
            authService.login(request)
        }
    }

    @Test
    fun `# login failed - 비밀번호 불일치 InvalidPasswordException`() {
        // given
        val user = User(id = 1L, email = "test@gmail.com", password = "encoded", status = UserStatus.REGISTERED)
        val request = LoginRequest(email = "test@gmail.com", password = "wrong")

        given(userRepository.findByEmail("test@gmail.com")).willReturn(user)
        given(passwordEncoder.matches("wrong", "encoded")).willReturn(false)

        // when
        // then
        assertThrows(InvalidPasswordException::class.java) {
            authService.login(request)
        }
    }
}