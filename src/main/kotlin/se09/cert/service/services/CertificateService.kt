package se09.cert.service.services

import se09.cert.service.dto.CertResponseDTO
import se09.cert.service.dto.CreateCertificateDTO
import se09.cert.service.models.Certificate
import se09.cert.service.repository.CertificateRepository
import se09.cert.service.ws.VaultService
import java.util.*
import javax.inject.Inject

class CertificateService {

    @Inject
    private lateinit var vaultService: VaultService

    @Inject
    private lateinit var certificateRepository: CertificateRepository

    fun createCertificate(createCertificateDTO: CreateCertificateDTO): CertResponseDTO {
        val vaultResponse = vaultService.generateCertificate(
                clientId = createCertificateDTO.clientId,
                type = createCertificateDTO.clientType
        )
        val clientUUID = UUID.fromString(createCertificateDTO.clientId)
        val certificate = Certificate(
                requestId = vaultResponse.requestId,
                serialNumber = vaultResponse.data.serialNumber,
                expiration = vaultResponse.data.expiration,
                clientId = clientUUID,
                clientType = createCertificateDTO.clientType
        )
        certificateRepository.save(certificate)
        return CertResponseDTO(
                certificate = vaultResponse.data.certificate,
                key = vaultResponse.data.privateKey
        )
    }

}