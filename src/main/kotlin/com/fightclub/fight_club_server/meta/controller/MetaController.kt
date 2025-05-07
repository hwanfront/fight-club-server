package com.fightclub.fight_club_server.meta.controller

import com.fightclub.fight_club_server.common.dto.BaseResponse
import com.fightclub.fight_club_server.meta.constants.MetaSuccessCode
import com.fightclub.fight_club_server.meta.enums.WeightClass
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/meta")
class MetaController {

    @GetMapping("/weight-classes")
    fun getWeightClasses() = BaseResponse.success(MetaSuccessCode.WEIGHT_CLASSES_SUCCESS, WeightClass.getWeightClasses())
}