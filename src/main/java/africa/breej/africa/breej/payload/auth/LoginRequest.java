package africa.breej.africa.breej.payload.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Email
    private String email;

    private String phoneNumber;

    @NotBlank
    private String password;

    // Getters and Setters (Omitted for brevity)
}
