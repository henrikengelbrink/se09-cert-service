package se09.cert.service.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "certificates")
data class Certificate(
        @Column(name = "request_id")
        val requestId: String = "",

        @Column(name = "serial_number")
        val serialNumber: String = "",

        val expiration: Float = 0.0F,

        @Column(name = "client_id")
        val clientId: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000"),

        @Enumerated(EnumType.STRING)
        @Column(name = "client_type")
        val clientType: ClientType = ClientType.DEVICE
) {

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @Type(type = "pg-uuid")
    lateinit var id: UUID

    @Column(name = "updated_at")
    var updatedAt: Instant = Instant.now()

    @Column(name = "created_at")
    var createdAt: Instant = Instant.now()

    @JsonIgnore
    @Column(name = "deleted_at")
    var deletedAt: Instant? = null

    @PrePersist
    fun prePersist() {
        updatedAt = Instant.now()
        createdAt = updatedAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

}