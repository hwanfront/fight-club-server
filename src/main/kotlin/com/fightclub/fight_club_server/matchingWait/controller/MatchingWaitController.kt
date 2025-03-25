package com.fightclub.fight_club_server.matchingWait.controller

import com.fightclub.fight_club_server.common.dto.ApiResponse
import com.fightclub.fight_club_server.matchingWait.constants.MatchingWaitSuccessCode
import com.fightclub.fight_club_server.matchingWait.dto.MatchingWaitRequest
import com.fightclub.fight_club_server.matchingWait.service.MatchingWaitService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/matching-wait")
class MatchingWaitController(
    private val matchingWaitService: MatchingWaitService
) {
    @GetMapping("/me")
    fun getMyMatchingWait() = ApiResponse.success(MatchingWaitSuccessCode.GET_MY_MATCHING_WAIT_SUCCESS, matchingWaitService.getMyMatchingWait())

    @PostMapping
    fun createMatchingWait(@Valid @RequestBody request: MatchingWaitRequest) = ApiResponse.success(MatchingWaitSuccessCode.CREATE_MATCHING_WAIT_SUCCESS, matchingWaitService.enrollMatchingWait(request))

    @DeleteMapping
    fun deleteMatchingWait() = ApiResponse.success(MatchingWaitSuccessCode.REMOVE_MATCHING_WAIT_SUCCESS, matchingWaitService.cancelMatchingWait())

    @PatchMapping
    fun updateMatchingWait(@Valid @RequestBody request: MatchingWaitRequest) = ApiResponse.success(MatchingWaitSuccessCode.UPDATE_MATCHING_WAIT_SUCCESS, matchingWaitService.updateMatchingWait(request))

    @GetMapping("/candidate")
    fun getRandomCandidateList() = ApiResponse.success(MatchingWaitSuccessCode.GET_CANDIDATE_LIST_SUCCESS, matchingWaitService.getCandidateList())
}