package com.fightclub.fight_club_server.security

import com.fightclub.fight_club_server.common.service.ImageService
import com.fightclub.fight_club_server.security.userinfo.OAuth2UserInfoFactory
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
    private val userRepository: UserRepository,
    private val imageService: ImageService
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

            return DefaultOAuth2User(
                oAuth2User.authorities,
                oAuth2UserAttributes,
                when (userRequest.clientRegistration.registrationId) {
                    "google" -> "sub"
                    "kakao" -> "id"
                    "naver" -> "response.id"
                    else -> "email"
                }
            )
        }

        val profileUrl = strategy.extractProfileUrl(oAuth2User.attributes)

        // 이미지 다운로드 및 파일 서버에 저장
        val savedImageUrl = profileUrl.let { url ->
            imageService.downloadAndSave(url, getFilenameWithExtension(url,"${provider.name}_${providerId}"))
        }

        userRepository.save(User(
            email = email,
            providerId = providerId,
            provider = provider,
            profileImageUrl = savedImageUrl,
            status = UserStatus.WAITING
        ))

        return oAuth2User
    }

    private fun getFilenameWithExtension(url: String, nickname: String): String {
        val extension = url.substringAfterLast('.', "")
        return if (extension.isNotEmpty()) {
            "$nickname.$extension"
        } else {
            "$nickname.jpg"
        }
    }
}
