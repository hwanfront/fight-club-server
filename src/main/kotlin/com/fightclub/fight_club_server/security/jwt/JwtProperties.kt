package com.fightclub.fight_club_server.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt") // 설정파일 jwt 값을 자동 매핑
class JwtProperties {
    // 일반적으로 var 는 선언과 동시에 초기화해야 함
    // 설정파일을 읽어오면서 값을 할당하므로 나중에 값이 주입될 것을 보장하기 위해 lateinit 사용
    // Long 타입은 사용할 수 없으므로 기본값 0
    // 추가로 access-token-validity -> accessTokenValidity 주의
    lateinit var secret: String
    var accessTokenValidity: Long = 0
    var refreshTokenValidity: Long = 0
}