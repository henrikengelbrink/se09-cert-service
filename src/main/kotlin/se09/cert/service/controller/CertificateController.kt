package se09.cert.service.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import se09.cert.service.dto.CertResponseDTO
import se09.cert.service.dto.CreateCertificateDTO
import se09.cert.service.services.CertificateService
import javax.inject.Inject

@Controller("/certificates")
class CertificateController {

    @Inject
    private lateinit var certificateService: CertificateService

    @Post(produces = [MediaType.APPLICATION_JSON])
    fun requestCert(
            @Body dto: CreateCertificateDTO
    ): HttpResponse<CertResponseDTO> {
        val responseDTO = certificateService.createCertificate(dto)
        return HttpResponse.ok(responseDTO)
    }

}
