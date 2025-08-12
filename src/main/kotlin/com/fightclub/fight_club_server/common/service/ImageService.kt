package com.fightclub.fight_club_server.common.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI
import java.net.URL

@Service
class ImageService(
    @Value("\${app.file-server.redirect-uri}") // yml 에서 값 주입
    private val redirectUri: String
) {
    private val webClient = WebClient.builder().baseUrl(redirectUri).build()

    fun downloadAndSave(imageUrl: String, filename: String?): String {
        try {
            val uri = URI(imageUrl)
            val url: URL = uri.toURL()

            val fileBytes = url.readBytes()

            val builder = MultipartBodyBuilder()

            val resource = object : ByteArrayResource(fileBytes) {
                override fun getFilename(): String? {
                    return filename
                }
            }

            builder.part("profile_image", resource)

            val response = webClient.post()
                .uri("/upload")
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(Map::class.java)
                .block()

            val fileUrl = response?.get("fileUrl") as String

            return fileUrl
        } catch (e: Exception) {
            println("파일 서버 전송 실패: ${e.message}")
            return "default_image_url"
        }
    }
}