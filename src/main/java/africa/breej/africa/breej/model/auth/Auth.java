package africa.breej.africa.breej.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;

@Getter
@Setter
@Document(collection = "project_breej_db_auth")
public class Auth {
    @Id
    private String id;

    @Email
    private String email;

    private String password;
}
