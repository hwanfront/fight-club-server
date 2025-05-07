package com.fightclub.fight_club_server.matchingWait.repository

import com.fightclub.fight_club_server.matchingWait.domain.MatchingWait
import com.fightclub.fight_club_server.matchingWait.dto.MatchingCandidateProjection
import com.fightclub.fight_club_server.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MatchingWaitRepository: JpaRepository<MatchingWait, Long> {
    fun findByUser(user: User): MatchingWait?
    fun findByUserId(userId: Long): MatchingWait?
    fun existsByUserId(userId: Long): Boolean

    // SELECT 1 -> 성능상 가볍고 의미상 "존재 여부만 본다"는 표현
    // JPA 에서 네이티브 쿼리 사용할 때, 안정성과 깔끔함 때문에 Projection 을 사용함
    // Entity 로 매핑하려면 조회한 컬럼이 Entity에 정의된 필드와 정확히 일치해야 하기 때문에
    // 조인하거나 필요한 필드만 뽑을 때 매핑 오류 발생 가능
    @Query(
        value = """
            SELECT mw.user_id AS userId, u.nickname AS nickname, mw.weight, mw.weight_class AS weightClass
            FROM matching_wait mw
            JOIN users u ON mw.user_id = u.id
            WHERE mw.weight_class = :weightClass
                AND mw.user_id != :userId
                AND NOT EXISTS (
                    SELECT 1 FROM match_proposal mp
                    WHERE mp.sender_id = :userId
                    AND mp.receiver_id = mw.user_id
                )
            ORDER BY RAND()
            LIMIT :limit
        """,
        nativeQuery = true
    )
    fun findCandidateListByWeightClassRandom(
        @Param("weightClass") weightClass: String,
        @Param("userId") userId: Long,
        limit: Int
    ): List<MatchingCandidateProjection>
}