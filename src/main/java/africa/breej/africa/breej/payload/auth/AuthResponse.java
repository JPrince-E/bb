package africa.breej.africa.breej.payload.auth;

import africa.breej.africa.breej.payload.user.UserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserResponse user;

    public AuthResponse(String accessToken, UserResponse user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    // Getters and Setters (Omitted for brevity)
}
