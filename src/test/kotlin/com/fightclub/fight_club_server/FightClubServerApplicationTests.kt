package com.fightclub.fight_club_server

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.fail
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FightClubServerApplicationTests {	

	@Test
	fun failTest() {
		fail("의도적으로 실패하는 테스트입니다.")
	}
}
