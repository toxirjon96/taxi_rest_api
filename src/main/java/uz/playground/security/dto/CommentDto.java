package uz.playground.security.dto;

public class CommentDto {
    private String secretKey;
    private String description;

    public CommentDto() {
    }

    public CommentDto(String secretKey, String description) {
        this.secretKey = secretKey;
        this.description = description;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "secretKey='" + secretKey + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
