package se09.cert.service.services

import se09.cert.service.models.ClientType
import se09.cert.service.ws.DeviceWebService
import se09.cert.service.ws.UserWebService
import se09.device.service.dto.VerneMQPublishDTO
import se09.device.service.dto.VerneMQRegisterDTO
import se09.device.service.dto.VerneMQSubscribeDTO
import javax.inject.Inject


class VerneMQService {

    @Inject
    private lateinit var deviceWebService: DeviceWebService

    @Inject
    private lateinit var userWebService: UserWebService

    fun credentialsValid(dto: VerneMQRegisterDTO): Boolean {
        return when(dto.clientType) {
            ClientType.DEVICE -> deviceCredentialsValid(dto)
            ClientType.USER -> userCredentialsValid(dto)
        }
    }

    private fun deviceCredentialsValid(dto: VerneMQRegisterDTO): Boolean {
        return deviceWebService.loginValid(dto)
    }

    private fun userCredentialsValid(dto: VerneMQRegisterDTO): Boolean {
        return userWebService.loginValid(dto)
    }

    fun isAllowedToSubscribe(dto: VerneMQSubscribeDTO): Boolean {
        return when(dto.clientType) {
            ClientType.DEVICE -> deviceIsAllowedToSubscribe(dto)
            ClientType.USER -> userIsAllowedToSubscribe(dto)
        }
    }

    private fun deviceIsAllowedToSubscribe(dto: VerneMQSubscribeDTO): Boolean {
        return dto.clientId == dto.deviceIdTopic()
    }

    private fun userIsAllowedToSubscribe(dto: VerneMQSubscribeDTO): Boolean {
        return deviceWebService.userPubSubAllowed(
                username = dto.username,
                clientId = dto.clientId,
                topic = dto.deviceIdTopic()
        )
    }

    fun isAllowedToPublish(dto: VerneMQPublishDTO): Boolean {
        return when(dto.clientType) {
            ClientType.DEVICE -> deviceIsAllowedToPublish(dto)
            ClientType.USER -> userIsAllowedToPublish(dto)
        }
    }

    private fun deviceIsAllowedToPublish(dto: VerneMQPublishDTO): Boolean {
        return dto.clientId == dto.deviceIdTopic()
    }

    private fun userIsAllowedToPublish(dto: VerneMQPublishDTO): Boolean {
        return deviceWebService.userPubSubAllowed(
                username = dto.username,
                clientId = dto.clientId,
                topic = dto.deviceIdTopic()
        )
    }

}
