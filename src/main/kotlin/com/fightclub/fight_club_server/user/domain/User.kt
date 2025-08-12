package com.fightclub.fight_club_server.user.domain

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // 일반 로그인용
    @Column(unique = true)
    val email: String? = null,
    // e: file:///Users/kjh/IdeaProjects/fight-club-server/src/main/kotlin/com/fightclub/fight_club_server/user/domain/User.kt:22:5 Platform declaration clash: The following declarations have the same JVM signature (getPassword()Ljava/lang/String;):
    //    fun `<get-password>`(): String? defined in com.fightclub.fight_club_server.user.domain.User
    //    fun getPassword(): String? defined in com.fightclub.fight_club_server.user.domain.User
    // JVM에서 같은 메서드 시그니처가 중복될 때 발생
    // User 클래스에서 password: String? 필드가 존재하는데,
    // UserDetails 인터페이스에서 getPassword()를 강제 구현하도록 요구하기 때문에 중복 충돌
    // 따라서 자동 생성되는 getter 이름을 변경한 것
    @get:JvmName("getUserPassword")
    var password: String? = null,

    @get:JvmName("getUserUsername")
    var username: String? = null,

    var nickname: String? = null,

    val providerId: String? = null,
    @Enumerated(EnumType.STRING)
    val provider: AuthProvider = AuthProvider.NONE,

    val profileImageUrl: String? = null,

    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.WAITING,

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.ROLE_USER
): UserDetails {
    fun updateProfile(nickname: String, username: String) {
        require(nickname.length in 2..20) { "닉네임은 2자 이상 20자 이하여야 합니다." }
        require(username.length in 2..50) { "사용자명은 2자 이상 50자 이하여야 합니다." }
        this.nickname = nickname
        this.username = username
        this.status = UserStatus.REGISTERED
    }

    fun deleteUser() {
        this.status = UserStatus.DELETED
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String? = password
    override fun getUsername(): String? = email
}
