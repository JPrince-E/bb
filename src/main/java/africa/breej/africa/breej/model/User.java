package africa.breej.africa.breej.model;

import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "project_breej_db_user")
public class User {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String username;

    @Email
    private String email;

    private Boolean emailVerified = false;

    private String password;

    private String courseOfStudy;

    private String department;

    private String faculty;

    private String gender;

    private String yearOfEntry;

    private String level;

    private String cgpa;

    private String phoneNumber;

}
