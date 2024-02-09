package uz.playground.security.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto {
    private String secretKey;
    private Long fromRegionId;
    private Long fromDistrictId;
    private Long toRegionId;
    private Long toDistrictId;
    private Long carId;
    @Schema(format = "YYYY-MM-DD")
    private String dateFrom;
    @Schema(format = "YYYY-MM-DD")
    private String dateTo;
    @Schema(format = "HH24:MM" , example = "10:00")
    private String timeFrom;
    @Schema(format = "HH24:MM" , example = "13:00")
    private String timeTo;
    private Integer personCount;
    private String description;
    @Schema(description = "Nomerlar vergul bilan ajratilgan xolda", example = "+998941234567, +998901234567")
    private String contactNumber;
    @Schema(description = "Minimal ketish narxi", example = "100000")
    private Double minPrice;
    @Schema(description = "Maximal ketish narxi", example = "120000")
    private Double maxPrice;


    public PostDto() {
    }

    public PostDto(String secretKey, Long fromRegionId,
                   Long fromDistrictId, Long toRegionId,
                   Long toDistrictId, Long carId, String dateFrom,
                   String dateTo, String timeFrom, String timeTo,
                   Integer personCount, String description,
                   String contactNumber, Double minPrice,
                   Double maxPrice) {
        this.secretKey = secretKey;
        this.fromRegionId = fromRegionId;
        this.fromDistrictId = fromDistrictId;
        this.toRegionId = toRegionId;
        this.toDistrictId = toDistrictId;
        this.carId = carId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.personCount = personCount;
        this.description = description;
        this.contactNumber = contactNumber;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Long getFromRegionId() {
        return fromRegionId;
    }

    public void setFromRegionId(Long fromRegionId) {
        this.fromRegionId = fromRegionId;
    }

    public Long getFromDistrictId() {
        return fromDistrictId;
    }

    public void setFromDistrictId(Long fromDistrictId) {
        this.fromDistrictId = fromDistrictId;
    }

    public Long getToRegionId() {
        return toRegionId;
    }

    public void setToRegionId(Long toRegionId) {
        this.toRegionId = toRegionId;
    }

    public Long getToDistrictId() {
        return toDistrictId;
    }

    public void setToDistrictId(Long toDistrictId) {
        this.toDistrictId = toDistrictId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "secretKey='" + secretKey + '\'' +
                ", fromRegionId=" + fromRegionId +
                ", fromDistrictId=" + fromDistrictId +
                ", toRegionId=" + toRegionId +
                ", toDistrictId=" + toDistrictId +
                ", carId=" + carId +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                ", timeFrom='" + timeFrom + '\'' +
                ", timeTo='" + timeTo + '\'' +
                ", personCount=" + personCount +
                ", description='" + description + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
