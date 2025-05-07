package com.fightclub.fight_club_server.config

import com.fightclub.fight_club_server.common.exception.UnauthorizedException
import com.fightclub.fight_club_server.security.jwt.TokenProvider
import com.fightclub.fight_club_server.user.exception.UserNotFoundException
import com.fightclub.fight_club_server.user.repository.UserRepository
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import java.security.Principal

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository
) : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/ws/sub") // server -> client
        registry.setApplicationDestinationPrefixes("/ws/pub") // client -> server
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
            .withSockJS()
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(object : ChannelInterceptor {
            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
                val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)!!

                if (accessor.command == StompCommand.CONNECT) {

                    val rawToken = accessor.getFirstNativeHeader("Authorization")
                        ?.removePrefix("Bearer ")
                        ?: throw UnauthorizedException()

                    if (!tokenProvider.validateToken(rawToken)) {
                        throw UnauthorizedException()
                    }

                    val userId = tokenProvider.getUserIdFromToken(rawToken)
                    val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }

                    accessor.user = Principal { user.email } // principal.name = user.email
                }

                return message
            }
        })
    }
}