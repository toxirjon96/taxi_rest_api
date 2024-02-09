package uz.playground.security.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteRoleDto {
    @Schema(description = "Xavfsizlik uchun maxsus kalit")
    private String secretKey;
    @Schema(description = "Foydalanuvchi roli", allowableValues = {"ROLE_DRIVER", "ROLE_USER"})
    private String roleName;

    public DeleteRoleDto() {
    }

    public DeleteRoleDto(String secretKey, String roleName) {
        this.secretKey = secretKey;
        this.roleName = roleName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "DeleteRoleDto{" +
                "secretKey='" + secretKey + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
