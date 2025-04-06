package com.fightclub.fight_club_server

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FightClubServerApplicationTests {	

	@Test
	fun failTest() {
		assertEquals(1, 2) // 실패 유도
	}
}
