package com.fightclub.fight_club_server.matchingWait.dto

import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits

data class MatchingWaitRequest (
    @field:DecimalMin("30.0", message = "몸무게는 최소 30kg 이상이어야 합니다.")
    @field:DecimalMax("200.0", message = "몸무게는 최대 200kg 이하여야 합니다.")
    @field:Digits(integer = 3, fraction = 1, message = "몸무게는 소수점 한 자리까지 입력 가능합니다.")
    val weight: Double,
)