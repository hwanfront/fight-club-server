package com.fightclub.fight_club_server.matchProposal.mapper

import com.fightclub.fight_club_server.match.domain.Match
import com.fightclub.fight_club_server.match.domain.MatchStatus
import com.fightclub.fight_club_server.matchProposal.domain.MatchProposal
import com.fightclub.fight_club_server.matchProposal.dto.AcceptResponse
import com.fightclub.fight_club_server.matchProposal.dto.ReceivedMatchProposalResponse
import com.fightclub.fight_club_server.matchProposal.dto.SentMatchProposalResponse
import org.springframework.stereotype.Component

@Component
class MatchProposalMapper {
    fun toReceivedMatchProposalResponse(matchProposal: MatchProposal): ReceivedMatchProposalResponse {
        return ReceivedMatchProposalResponse(
            id = matchProposal.id,
            senderNickname = matchProposal.sender.nickname,
            senderWeight = matchProposal.senderWeight,
            weightClass = matchProposal.weightClass,
            requestedAt = matchProposal.requestedAt,
        )
    }

    fun toSentMatchProposalResponse(matchProposal: MatchProposal): SentMatchProposalResponse {
        return SentMatchProposalResponse(
            id = matchProposal.id,
            senderWeight = matchProposal.senderWeight,
            receiverWeight = matchProposal.receiverWeight,
            receiverNickname = matchProposal.receiver.nickname,
            weightClass = matchProposal.weightClass,
            requestedAt = matchProposal.requestedAt,
        )
    }

    fun toMatch(matchProposal: MatchProposal): Match {
        return Match(
            user1 = matchProposal.receiver,
            user2 = matchProposal.sender,
            user1Weight = matchProposal.receiverWeight,
            user2Weight = matchProposal.senderWeight,
            weightClass = matchProposal.weightClass,
            status = MatchStatus.CHATTING,
        )
    }

    fun toAcceptResponse(match: Match): AcceptResponse {
        return AcceptResponse(
            matchId = match.id
        )
    }
}
