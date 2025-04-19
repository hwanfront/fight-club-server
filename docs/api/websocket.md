# WebSocket API 문서

## ✅ 기본 정보
- WebSocket 연결 주소: `ws://<host>/ws`
- SockJS 사용
- PUB (Client → Server): `/ws/pub`
- SUB (Server → Client): `/ws/sub`
- 인증 헤더:
  Authorization: Bearer {accessToken}

### 매치 Ready 상태 업데이트
```
PUB: /ws/pub/match.ready  
SUB: /ws/sub/match/room/{matchId}  

Request: ReadyRequest

    ReadyRequest:
    {
        matchId: Long
    }  

Response: {"code": "MATCH_READY_UPDATED", "message": "사용자 준비 상태가 변경되었습니다.", "data": MatchReadyResponse}

    MatchReadyResponse:
    {
        matchId: Long 
        isMeReady: Boolean
        isOpponentReady: Boolean
        matchStatus: MatchStatus
    }
    
    MatchStatus:
    "CHATTING" | "READY_TO_STREAM" | "STREAMING" | "PAUSED" | "ENDED" | "DECLINED" 

Exception: 
{"code": "ME001", "message": "매치를 찾을 수 없습니다."}  
{"code": "ME002", "message": "사용자가 매치 참가자가 아닙니다."}
```

### 매치 거절
```
PUB: /ws/pub/match.decline  
SUB: /ws/sub/match/room/{matchId}  

Request: DeclineRequest
    
    DeclineRequest:
    {
        matchId: Long
    }  

Response: {"code": "DECLINE_MATCH", "message": "매치가 거절되었습니다.", "data": DeclineMatchResponse}

    DeclineMatchResponse:
    {
        matchId: Long
        declinedBy: String
    }  

Exception: 
{"code": "ME001", "message": "매치를 찾을 수 없습니다."}  
{"code": "ME002", "message": "사용자가 매치 참가자가 아닙니다."}
```

### 💬 채팅 메시지 전송
```
PUB: /ws/pub/match.chat  
SUB: /ws/sub/match/room/{matchId}  

Request: ChatMessageRequest

    ChatMessageRequest:
    {
        matchId: Long 
        content: String 
        messageType: ChatMessageType
    }  
    
    ChatMessageType:
    "TEXT" | "IMAGE"

Response: 
{"code": "NEW_CHAT_MESSAGE_RECEIVED", "message": "채팅 메시지를 수신했습니다.", "data": ChatMessageResponse} 
    
    ChatMessageResponse:
    {
        id: Long
        senderId: Long 
        senderNickname: String 
        content: String 
        messageType: ChatMessageType
        createdAt: LocalDateTime
    } 
    
    ChatMessageType:
    "TEXT" | "IMAGE"

Exception: 
{"code": "ME001", "message": "매치를 찾을 수 없습니다."}  
{"code": "ME002", "message": "사용자가 매치 참가자가 아닙니다."}
```

### ✅ 메시지 읽음 처리
```
PUB: /ws/pub/match.read  
SUB: /ws/sub/match/room/{matchId}  

Request: ReadChatMessageRequest

    ReadChatMessageRequest:  
    {
        matchId: Long
        messageId: Long
    }
    
Response: {"code": "READ_CHAT_MESSAGE", "message": "메시지 읽음 처리를 성공하였습니다.", "data": ReadChatMessageResponse}
    
    ReadChatMessageResponse:
    {
        lastReadMessageId: Long
    }
  
Exception: 
{"code": "ME001", "message": "매치를 찾을 수 없습니다."}  
{"code": "ME002", "message": "사용자가 매치 참가자가 아닙니다."}
{"code": "MRE001", "message": "이미 모든 메시지를 읽었습니다."}
```

### ✍️ 타이핑 중 알림
```
PUB: /ws/pub/match.typing  
SUB: /ws/sub/match/room/{matchId}  

Request: TypingStatusRequest

    TypingStatusRequest:
    {
        matchId: Long
        isTyping: Boolean
    }
  
Response: {"code": "TYPING_STATUS_RECEIVED", "message": "상대방의 입력 상태를 확인하였습니다.", "data": TypingStatusResponse}

    TypingStatusResponse:
    {
        userId: Long
        isTyping: Boolean
    }
  
Exception: 
{"code": "ME001", "message": "매치를 찾을 수 없습니다."}  
{"code": "ME002", "message": "사용자가 매치 참가자가 아닙니다."}
```
