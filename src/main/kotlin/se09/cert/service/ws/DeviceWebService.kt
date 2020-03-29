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
class DeviceWebService {

    @Value("\${service.url.device}")
    private lateinit var deviceServiceUrl: String

    private val LOG: Logger = LoggerFactory.getLogger(DeviceWebService::class.java)

    fun loginValid(dto: VerneMQRegisterDTO): Boolean {
        LOG.info("loginValid")
        val httpClient = RxHttpClient.create(URL(deviceServiceUrl))
        val payload = mapOf(
                "username" to dto.username,
                "password" to dto.password,
                "clientId" to dto.clientId
        )
        val response = httpClient.toBlocking().exchange(
                POST("/mqtt/register", payload).contentType(MediaType.APPLICATION_JSON),
                Map::class.java
        )
        LOG.info("loginValid status: ${response.status.code}")
        return response.status == HttpStatus.OK
    }

    fun userPubSubAllowed(username: String, clientId: String, topic: String): Boolean {
        LOG.info("userPubSubAllowed")
        val httpClient = RxHttpClient.create(URL(deviceServiceUrl))
        val payload = mapOf(
                "username" to username,
                "clientId" to clientId,
                "topic" to topic
        )
        val response = httpClient.toBlocking().exchange(
                POST("/mqtt/user/topic", payload).contentType(MediaType.APPLICATION_JSON),
                Map::class.java
        )
        LOG.info("loginValid status: ${response.status.code}")
        return response.status == HttpStatus.OK
    }

}
