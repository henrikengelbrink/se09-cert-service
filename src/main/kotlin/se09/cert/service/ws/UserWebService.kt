package se09.cert.service.ws

import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpRequest.POST
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.RxHttpClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import se09.device.service.dto.VerneMQRegisterDTO
import java.net.URL
import javax.inject.Singleton

@Singleton
class UserWebService {

    @Value("\${service.url.user}")
    private lateinit var userServiceUrl: String

    private val LOG: Logger = LoggerFactory.getLogger(UserWebService::class.java)

    fun loginValid(dto: VerneMQRegisterDTO): Boolean {
        LOG.info("loginValid")
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
        LOG.info("loginValid status: ${response.status.code}")
        return response.status == HttpStatus.OK
    }

}
