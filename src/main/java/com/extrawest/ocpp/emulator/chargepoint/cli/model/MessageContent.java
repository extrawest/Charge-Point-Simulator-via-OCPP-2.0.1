package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;


/**
 * Message_ Content
 * urn:x-enexis:ecdm:uid:2:234490
 * Contains message details, for a message to be displayed on a Charging Station.
 *
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "format",
        "language",
        "content"
})
@Builder
@Getter
public class MessageContent {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * Message_ Content. Format. Message_ Format_ Code
     * urn:x-enexis:ecdm:uid:1:570848
     * Format of the message.
     *
     * (Required)
     *
     */
    @JsonProperty("format")
    public MessageFormatEnum format;
    /**
     * Message_ Content. Language. Language_ Code
     * urn:x-enexis:ecdm:uid:1:570849
     * Message language identifier. Contains a language code as defined in &lt;<ref-RFC5646,[RFC5646]>>.
     *
     *
     */
    @JsonProperty("language")
    public String language;
    /**
     * Message_ Content. Content. Message
     * urn:x-enexis:ecdm:uid:1:570852
     * Message contents.
     *
     *
     * (Required)
     *
     */
    @JsonProperty("content")
    public String content;

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.format == null)? 0 :this.format.hashCode()));
        result = ((result* 31)+((this.customData == null)? 0 :this.customData.hashCode()));
        result = ((result* 31)+((this.language == null)? 0 :this.language.hashCode()));
        result = ((result* 31)+((this.content == null)? 0 :this.content.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MessageContent rhs)) {
            return false;
        }
        return ((((Objects.equals(this.format, rhs.format))&&(Objects.equals(this.customData, rhs.customData)))&&(Objects.equals(this.language, rhs.language)))&&(Objects.equals(this.content, rhs.content)));
    }

}
