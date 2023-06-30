package africa.breej.africa.breej.payload.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class UpdateUserPasswordRequest implements Serializable {
    @NotEmpty(message = "current password is required")
    @NotNull
    private String currentPassword;
    @NotEmpty(message = "new password is required")
    @NotNull
    private String newPassword;
}
