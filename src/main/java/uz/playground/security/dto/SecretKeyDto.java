package uz.playground.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecretKeyDto {
    @Schema(description = "Xavfsizlik uchun maxsus kalit")
    private String secretKey;
    public SecretKeyDto() {
    }
    public SecretKeyDto(String secretKey) {
        this.secretKey = secretKey;
    }
    public String getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}