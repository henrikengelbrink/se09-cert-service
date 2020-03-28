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
        LOG.info("getUserIdFromToken")
        val httpClient = RxHttpClient.create(URL(deviceServiceUrl))
        val payload = mapOf(
                "username" to dto.username,
                "password" to dto.password,
                "clientId" to dto.clientId
        )
        val response = httpClient.toBlocking().exchange(
                POST("/device/mqtt/auth", payload).contentType(MediaType.APPLICATION_JSON),
                Map::class.java
        )
        return response.status == HttpStatus.OK
    }

}
