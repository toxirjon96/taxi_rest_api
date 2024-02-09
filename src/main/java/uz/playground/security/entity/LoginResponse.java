package uz.playground.security.entity;
public class LoginResponse {
    private String accessToken;
    private String status;
    private String roleName;
    public LoginResponse() {
    }
    public LoginResponse(String accessToken, String status, String roleName) {
        this.accessToken = accessToken;
        this.status = status;
        this.roleName = roleName;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}