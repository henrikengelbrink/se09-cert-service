package se09.device.service.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class VerneMQPublishDTO(
        @JsonProperty("client_id")
        override val username: String,

        @JsonProperty("username")
        override val fullClientId: String,

        val topic: String
): VerneMQEventDTO {

    fun deviceIdTopic(): String {
        return if(topic.contains("/")) topic.substringBefore("/")
        else topic
    }

}
