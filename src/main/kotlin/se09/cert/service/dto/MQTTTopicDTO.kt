package se09.cert.service.dto

data class MQTTTopicDTO(
        val topic: String,
        val qos: Int
)