package com.fightclub.fight_club_server.meta.exception

import com.fightclub.fight_club_server.common.exception.BaseException
import com.fightclub.fight_club_server.meta.constants.MetaErrorCode

class WeightClassNotFoundException : BaseException(MetaErrorCode.WEIGHT_CLASS_NOT_FOUND)
