package uz.playground.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverCarDto {
    private String carName;
    private String carColor;
    private Integer personCount;
    private String carNumber;
    @Schema(description = "Xavfsizlik uchun maxsus kalit")
    private String secretKey;

    public DriverCarDto() {
    }

    public DriverCarDto(String roleName,
                        String carName, String carColor,
                        Integer personCount, String carNumber,
                        String secretKey) {
        this.carName = carName;
        this.carColor = carColor;
        this.personCount = personCount;
        this.carNumber = carNumber;
        this.secretKey = secretKey;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "DriverCarDto{" +
                " carName='" + carName + '\'' +
                ", carColor='" + carColor + '\'' +
                ", personCount=" + personCount +
                ", carNumber='" + carNumber + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
