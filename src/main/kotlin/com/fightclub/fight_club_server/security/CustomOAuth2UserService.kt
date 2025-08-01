package com.fightclub.fight_club_server.security

import com.fightclub.fight_club_server.security.userinfo.OAuth2UserInfoFactory
import com.fightclub.fight_club_server.user.domain.AuthProvider
import com.fightclub.fight_club_server.user.domain.User
import com.fightclub.fight_club_server.user.domain.UserStatus
import com.fightclub.fight_club_server.user.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository
): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)
        val strategy = OAuth2UserInfoFactory.getStrategy(userRequest.clientRegistration.registrationId)

        val provider = strategy.provider
        val providerId = strategy.extractProviderId(oAuth2User.attributes)
        val email = strategy.extractEmail(oAuth2User.attributes)
        val existingUser = userRepository.findByProviderAndProviderId(provider, providerId)

        if (existingUser != null) {
            return oAuth2User
        }

        val existingUserByEmail = email.let { userRepository.findByEmail(it) }

        if (existingUserByEmail != null) {
            val oAuth2UserAttributes = oAuth2User.attributes.toMutableMap()
            oAuth2UserAttributes["emailAlreadyExists"] = true
            oAuth2UserAttributes["existingUserId"] = existingUserByEmail.id

            // DefaultOAuth2User의 생성자를 사용하여 새로운 OAuth2User 객체 생성
            return DefaultOAuth2User(
                oAuth2User.authorities,
                oAuth2UserAttributes,
                when (userRequest.clientRegistration.registrationId) {
                    "google" -> "sub"
                    "kakao" -> "id"
                    "naver" -> "response.id"
                    // 다른 제공자가 있다면 여기에 추가
                    else -> "email" // 기본값 설정
                } // nameAttributeKey, 필요에 따라 수정
            )
        }

        userRepository.save(User(
            email = email,
            providerId = providerId,
            provider = provider,
            status = UserStatus.WAITING
        ))

        return oAuth2User
    }
}
