package uz.playground.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupDto {
    private String surname;
    private String name;
    private String patronym;
    @Schema(format = "YYYY-MM-DD")
    private String dateOfBirth;
    private String phoneNumber;
    private String email;
    private String password;
    @Schema(description = "Tizim tili uchun", allowableValues = {"EN", "RU", "UZ"})
    private String lang;
    @Schema(description = "Foydalanuvchi roli", allowableValues = {"ROLE_DRIVER", "ROLE_USER", "ROLE_ADMIN"})
    private String roleName;
    @Schema(description = "Xavfsizlik uchun maxsus kalit")
    private String secretKey;
    @Schema(description = "Rasm kiritilish majburiy emas!", format = "base64")
    private String image;

    public SignupDto() {

    }

    public SignupDto(String surname, String name,
                     String patronym, String dateOfBirth,
                     String phoneNumber, String email,
                     String password, String lang, String roleName,
                     String secretKey, String image) {
        this.surname = surname;
        this.name = name;
        this.patronym = patronym;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.lang = lang;
        this.roleName = roleName;
        this.secretKey = secretKey;
        this.image = image;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronym() {
        return patronym;
    }

    public void setPatronym(String patronym) {
        this.patronym = patronym;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "SignupDto{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronym='" + patronym + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", lang='" + lang + '\'' +
                ", roleName='" + roleName + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
