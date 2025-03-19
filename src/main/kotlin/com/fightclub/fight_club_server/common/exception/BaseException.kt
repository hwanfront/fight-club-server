package com.fightclub.fight_club_server.common.exception

import com.fightclub.fight_club_server.common.constants.ResponseCode

// kotlin 에서 기본적으로 클래스는 상속불가 final
// -> open 키워드를 통해 상속 가능하게 만듦
// java 는 모든 클래스가 기본적으로 상속 가능하지만
// java 의 abstract class 는 무조건 상속 해야하는 반면에
// open class 는 선택적으로 상속 가능함
open class BaseException(
    private val responseCode: ResponseCode
) : RuntimeException(responseCode.message) {
    val errorCode: ResponseCode = responseCode
}