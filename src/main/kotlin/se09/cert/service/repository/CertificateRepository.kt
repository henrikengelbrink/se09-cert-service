package se09.cert.service.repository

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import se09.cert.service.models.Certificate
import java.util.*

@Repository
interface CertificateRepository : CrudRepository<Certificate, UUID>
