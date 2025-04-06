package com.fightclub.fight_club_server

import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

class PlainJUnitTest {

    @Test
    fun forceFail() {
        fail("강제로 실패하는 테스트입니다.")
    }
}
