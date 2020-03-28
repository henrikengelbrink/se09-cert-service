package se09.cert.service.services

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import se09.cert.service.models.ClientType
import se09.cert.service.ws.DeviceWebService
import se09.device.service.dto.VerneMQRegisterDTO
import se09.device.service.dto.VerneMQSubscribeDTO
import javax.inject.Inject


class VerneMQService {

    @Inject
    private lateinit var deviceWebService: DeviceWebService

    private val LOG: Logger = LoggerFactory.getLogger(VerneMQService::class.java)

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
        LOG.info("userCredentialsValid")
        LOG.info("username ${dto.username}")
        LOG.info("password ${dto.password}")
        LOG.info("clientId ${dto.clientId}")
        LOG.info("clientType ${dto.clientType}")
        LOG.info("fullClientId ${dto.fullClientId}")
        return true
    }

    fun isAllowedToSubscribe(dto: VerneMQSubscribeDTO): Boolean {
        return when(dto.clientType) {
            ClientType.DEVICE -> deviceIsAllowedToSubscribe(dto)
            ClientType.USER -> userIsAllowedToSubscribe(dto)
        }
    }

    private fun deviceIsAllowedToSubscribe(dto: VerneMQSubscribeDTO): Boolean {
        // Todo
        return true
    }

    private fun userIsAllowedToSubscribe(dto: VerneMQSubscribeDTO): Boolean {
        // Todo
        return true
    }

}
