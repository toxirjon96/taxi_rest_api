package uz.playground.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistrictRequestDto {
    @Schema(description = "Xavfsizlik uchun maxsus kalit")
    private String secretKey;
    private Long regionId;

    public DistrictRequestDto() {
    }

    public DistrictRequestDto(String secretKey, Long regionId) {
        this.secretKey = secretKey;
        this.regionId = regionId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return "DistrictRequestDto{" +
                "secretKey='" + secretKey + '\'' +
                ", regionId=" + regionId +
                '}';
    }
}
