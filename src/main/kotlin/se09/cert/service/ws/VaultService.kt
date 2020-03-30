package se09.cert.service.ws

import com.beust.klaxon.Klaxon
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import se09.cert.service.models.ClientType
import se09.device.service.dto.VaultCertResponseDTO
import se09.device.service.dto.VaultLoginResponseDTO
import java.io.BufferedReader
import java.io.File
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VaultService(
        @Client("vault")
        @Inject val httpClient: RxHttpClient
) {

    fun generateCertificate(clientId: String, type: ClientType): VaultCertResponseDTO {
        val token = getVaultToken()
        val ttlSeconds = 2181246468 - Instant.now().epochSecond
        val hours = ttlSeconds / 3600

        val body = mapOf(
                "common_name" to "$clientId.${type.name.toLowerCase()}.engelbrink.dev",
                "ttl" to "${hours}h"
        )
        val request = HttpRequest.POST("/v1/pki_int/issue/engelbrink-dev", body)
        request.headers.add("Content-Type", "application/json")
        request.headers.add("X-Vault-Token", token)
        val response = httpClient.toBlocking().retrieve(request)
        val jsonResponse = Klaxon().parse<VaultCertResponseDTO>(response)
        return jsonResponse!!
    }

    private fun getVaultToken(): String {
        val bufferedReader: BufferedReader = File("/var/run/secrets/kubernetes.io/serviceaccount/token").bufferedReader()
        val token = bufferedReader.use { it.readText() }
        val body = mapOf(
                "role" to "cert-service-role",
                "jwt" to token
        )
        val request = HttpRequest.POST("/v1/auth/kubernetes/login", body)
        request.headers.add("Content-Type", "application/json")
        val response = httpClient.toBlocking().retrieve(request)
        val jsonResponse = Klaxon().parse<VaultLoginResponseDTO>(response)
        return jsonResponse!!.auth.token
    }



}
