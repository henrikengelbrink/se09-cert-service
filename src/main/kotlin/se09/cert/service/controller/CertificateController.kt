package se09.cert.service.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import se09.cert.service.dto.CertResponseDTO
import se09.cert.service.dto.CreateCertificateDTO
import se09.cert.service.services.CertificateService
import javax.inject.Inject

@Controller("/certificates")
class CertificateController {

    private val LOG: Logger = LoggerFactory.getLogger(CertificateController::class.java)

    @Inject
    private lateinit var certificateService: CertificateService

    @Post(produces = [MediaType.APPLICATION_JSON])
    fun requestCert(
            @Body dto: CreateCertificateDTO
    ): HttpResponse<CertResponseDTO> {
        LOG.warn("########### requestCert")
        val responseDTO = certificateService.createCertificate(dto)
        return HttpResponse.ok(responseDTO)
    }

}
