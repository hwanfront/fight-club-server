package com.fightclub.fight_club_server.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class TokenProvider(private val jwtProperties: JwtProperties) {
    // lazy -> 최초 호출 시 한번만 생성
    private val key: Key by lazy {
        // require -> 특정 조건을 검사하고 조건을 만족하지 않으면 예외를 발생시키는 함수
        // Precondition 검증용으로, 예외가 발생하면 바로 프로그램 종료 (try catch 는 프로그램 계속 실행가능)
        require(jwtProperties.secret.toByteArray().size >= 32) {
            "JWT secret key must be at least 32 bytes"
        }
        Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())
    }

    fun generateAccessToken(userId: Long): String {
        val now = Date()
        val validity = Date(now.time + jwtProperties.accessTokenValidity)

        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(now)
            .setExpiration(validity)
            .setIssuer("fightclub")
            .setAudience("access_token")
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateRefreshToken(userId: Long): String {
        val now = Date()
        val validity = Date(now.time + jwtProperties.refreshTokenValidity)

        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(now)
            .setExpiration(validity)
            .setIssuer("fightclub")
            .setAudience("refresh_token")
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
            return !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            println(e.message)
            return false
        }
    }

    fun getUserIdFromToken(token: String): Long {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
            return claims.body.subject.toLong()
        } catch(e: Exception) {
            throw RuntimeException("Invalid JWT token", e)
        }
    }
}