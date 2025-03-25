package com.fightclub.fight_club_server.meta.enums

import com.fightclub.fight_club_server.meta.dto.WeightClassResponse
import com.fightclub.fight_club_server.meta.exception.WeightClassNotFoundException

enum class WeightClass(
    val displayName: String,
    val upperLimit: Double?
) {
    LIGHT_FLY("라이트플라이", 49.0),
    FLY("플라이", 52.0),
    BANTAM("밴텀", 56.0),
    FEATHER("페더", 60.0),
    LIGHT("라이트", 64.0),
    LIGHT_WELTER("라이트웰터", 69.0),
    WELTER("웰터", 75.0),
    LIGHT_MIDDLE("라이트미들", 81.0),
    MIDDLE("미들", 86.0),
    LIGHT_HEAVY("라이트헤비", 91.0),
    HEAVY("헤비", 97.0),
    SUPER_HEAVY("슈퍼헤비", null);

    companion object {
        fun getWeightClasses(): List<WeightClassResponse> {
            return WeightClass.entries.map {
                WeightClassResponse(
                    name = it.name,
                    displayName = it.displayName,
                    upperLimit = it.upperLimit
                )
            }
        }

        fun fromWeight(weight: Double): WeightClass {
            return WeightClass.entries.firstOrNull { it.upperLimit == null || weight <= it.upperLimit }
                ?: SUPER_HEAVY
        }

        fun fromName(name: String): WeightClass {
            try {
                return WeightClass.valueOf(name)
            } catch (e: IllegalArgumentException) {
                throw WeightClassNotFoundException()
            }
        }
    }

}