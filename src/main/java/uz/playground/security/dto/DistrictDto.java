package uz.playground.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistrictDto {
    @JsonProperty("SP_ID")
    private String SP_ID;
    @JsonProperty("SP_NAME00")
    private String SP_NAME00;
    @JsonProperty("SP_NAME01")
    private String SP_NAME01;
    @JsonProperty("SP_NAME02")
    private String SP_NAME02;
    @JsonProperty("SP_NAME03")
    private String SP_NAME03;
    @JsonProperty("SP_NAME04")
    private String SP_NAME04;
    @JsonProperty("SP_NAME05")
    private String SP_NAME05;
    @JsonProperty("SP_REGION")
    private String SP_REGION;
    @JsonProperty("SP_ACTIVE")
    private String SP_ACTIVE;
    @JsonProperty("SP_DATEENTE")
    private String SP_DATEENTE;
    @JsonProperty("SP_SCN")
    private String SP_SCN;
    @JsonProperty("SP_IDOLD")
    private String SP_IDOLD;
    @JsonProperty("SP_IDFIRST")
    private String SP_IDFIRST;

    public DistrictDto() {
    }

    public DistrictDto(String SP_ID, String SP_NAME00,
                       String SP_NAME01, String SP_NAME02,
                       String SP_NAME03, String SP_NAME04,
                       String SP_NAME05, String SP_REGION,
                       String SP_ACTIVE, String SP_DATEENTE,
                       String SP_SCN, String SP_IDOLD,
                       String SP_IDFIRST) {
        this.SP_ID = SP_ID;
        this.SP_NAME00 = SP_NAME00;
        this.SP_NAME01 = SP_NAME01;
        this.SP_NAME02 = SP_NAME02;
        this.SP_NAME03 = SP_NAME03;
        this.SP_NAME04 = SP_NAME04;
        this.SP_NAME05 = SP_NAME05;
        this.SP_REGION = SP_REGION;
        this.SP_ACTIVE = SP_ACTIVE;
        this.SP_DATEENTE = SP_DATEENTE;
        this.SP_SCN = SP_SCN;
        this.SP_IDOLD = SP_IDOLD;
        this.SP_IDFIRST = SP_IDFIRST;
    }

    public String getSP_ID() {
        return SP_ID;
    }

    public void setSP_ID(String SP_ID) {
        this.SP_ID = SP_ID;
    }

    public String getSP_NAME00() {
        return SP_NAME00;
    }

    public void setSP_NAME00(String SP_NAME00) {
        this.SP_NAME00 = SP_NAME00;
    }

    public String getSP_NAME01() {
        return SP_NAME01;
    }

    public void setSP_NAME01(String SP_NAME01) {
        this.SP_NAME01 = SP_NAME01;
    }

    public String getSP_NAME02() {
        return SP_NAME02;
    }

    public void setSP_NAME02(String SP_NAME02) {
        this.SP_NAME02 = SP_NAME02;
    }

    public String getSP_NAME03() {
        return SP_NAME03;
    }

    public void setSP_NAME03(String SP_NAME03) {
        this.SP_NAME03 = SP_NAME03;
    }

    public String getSP_NAME04() {
        return SP_NAME04;
    }

    public void setSP_NAME04(String SP_NAME04) {
        this.SP_NAME04 = SP_NAME04;
    }

    public String getSP_NAME05() {
        return SP_NAME05;
    }

    public void setSP_NAME05(String SP_NAME05) {
        this.SP_NAME05 = SP_NAME05;
    }

    public String getSP_REGION() {
        return SP_REGION;
    }

    public void setSP_REGION(String SP_REGION) {
        this.SP_REGION = SP_REGION;
    }

    public String getSP_ACTIVE() {
        return SP_ACTIVE;
    }

    public void setSP_ACTIVE(String SP_ACTIVE) {
        this.SP_ACTIVE = SP_ACTIVE;
    }

    public String getSP_DATEENTE() {
        return SP_DATEENTE;
    }

    public void setSP_DATEENTE(String SP_DATEENTE) {
        this.SP_DATEENTE = SP_DATEENTE;
    }

    public String getSP_SCN() {
        return SP_SCN;
    }

    public void setSP_SCN(String SP_SCN) {
        this.SP_SCN = SP_SCN;
    }

    public String getSP_IDOLD() {
        return SP_IDOLD;
    }

    public void setSP_IDOLD(String SP_IDOLD) {
        this.SP_IDOLD = SP_IDOLD;
    }

    public String getSP_IDFIRST() {
        return SP_IDFIRST;
    }

    public void setSP_IDFIRST(String SP_IDFIRST) {
        this.SP_IDFIRST = SP_IDFIRST;
    }

    @Override
    public String toString() {
        return "DistrictDto{" +
                "SP_ID='" + SP_ID + '\'' +
                ", SP_NAME00='" + SP_NAME00 + '\'' +
                ", SP_NAME01='" + SP_NAME01 + '\'' +
                ", SP_NAME02='" + SP_NAME02 + '\'' +
                ", SP_NAME03='" + SP_NAME03 + '\'' +
                ", SP_NAME04='" + SP_NAME04 + '\'' +
                ", SP_NAME05='" + SP_NAME05 + '\'' +
                ", SP_REGION='" + SP_REGION + '\'' +
                ", SP_ACTIVE='" + SP_ACTIVE + '\'' +
                ", SP_DATEENTE='" + SP_DATEENTE + '\'' +
                ", SP_SCN='" + SP_SCN + '\'' +
                ", SP_IDOLD='" + SP_IDOLD + '\'' +
                ", SP_IDFIRST='" + SP_IDFIRST + '\'' +
                '}';
    }
}
