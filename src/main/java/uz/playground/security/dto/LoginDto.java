package uz.playground.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto {
    @Schema(description = "Foydalanuvchi telefon raqami", example = "+998991234567")
    private String username;
    private  String password;
    @Schema(description = "Foydalanuvchi roli", allowableValues = {"ROLE_DRIVER", "ROLE_USER", "ROLE_ADMIN"})
    private String roleName;
    @Schema(description = "Xavfsizlik uchun maxsus kalit")
    private String secretKey;
    public LoginDto() {
    }

    public LoginDto(String username, String password, String roleName, String secretKey) {
        this.username = username;
        this.password = password;
        this.roleName = roleName;
        this.secretKey = secretKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "LoginDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roleName='" + roleName + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}