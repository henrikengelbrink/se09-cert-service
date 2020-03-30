package se09.cert.service.ws

import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpRequest.POST
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.RxHttpClient
import se09.device.service.dto.VerneMQRegisterDTO
import java.net.URL
import javax.inject.Singleton

@Singleton
class UserWebService {

    @Value("\${service.url.user}")
    private lateinit var userServiceUrl: String

    fun loginValid(dto: VerneMQRegisterDTO): Boolean {
        val httpClient = RxHttpClient.create(URL(userServiceUrl))
        val payload = mapOf(
                "username" to dto.username,
                "password" to dto.password,
                "clientId" to dto.clientId
        )
        val response = httpClient.toBlocking().exchange(
                POST("/mqtt/register", payload).contentType(MediaType.APPLICATION_JSON),
                String::class.java
        )
        return response.status == HttpStatus.OK
    }

}
