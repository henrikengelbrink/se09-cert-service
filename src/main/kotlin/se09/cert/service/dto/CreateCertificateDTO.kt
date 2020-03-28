package se09.cert.service.dto

import se09.cert.service.models.ClientType

data class CreateCertificateDTO(
    val clientId: String,
    val clientType: ClientType
)
