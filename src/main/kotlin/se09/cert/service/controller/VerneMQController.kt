package se09.cert.service.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import se09.cert.service.services.VerneMQService
import se09.device.service.dto.VerneMQPublishDTO
import se09.device.service.dto.VerneMQRegisterDTO
import se09.device.service.dto.VerneMQSubscribeDTO
import javax.inject.Inject

@Controller("/vernemq")
class VerneMQController {

    @Inject
    private lateinit var verneMQService: VerneMQService

    @Post("/auth_on_register", produces = [MediaType.APPLICATION_JSON])
    fun authOnRegister(@Body dto: VerneMQRegisterDTO): HttpResponse<Map<String, Any>> {
        return if (verneMQService.credentialsValid(dto)) {
            HttpResponse.ok(
                    mapOf(
                            "result" to "ok"
                    )
            )
        } else {
            HttpResponse.ok(
                    mapOf(
                            "result" to mapOf(
                                    "error" to "not_allowed")
                    )
            )
        }
    }

    @Post("/auth_on_subscribe", produces = [MediaType.APPLICATION_JSON])
    fun authOnSubscribe(@Body dto: VerneMQSubscribeDTO): HttpResponse<Map<String, Any>> {
        return if (verneMQService.isAllowedToSubscribe(dto)) {
            HttpResponse.ok(
                    mapOf(
                            "result" to "ok"
                    )
            )
        } else {
            HttpResponse.ok(
                    mapOf(
                            "result" to mapOf(
                                    "error" to "not_allowed")
                    )
            )
        }
    }

    @Post("/auth_on_publish", produces = [MediaType.APPLICATION_JSON])
    fun authOnPublish(@Body dto: VerneMQPublishDTO): HttpResponse<Map<String, Any>> {
        return if (verneMQService.isAllowedToPublish(dto)) {
            HttpResponse.ok(
                    mapOf(
                            "result" to "ok"
                    )
            )
        } else {
            HttpResponse.ok(
                    mapOf(
                            "result" to mapOf(
                                    "error" to "not_allowed")
                    )
            )
        }
    }



}