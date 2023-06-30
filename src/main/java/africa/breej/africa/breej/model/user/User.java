package africa.breej.africa.breej.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import africa.breej.africa.breej.model.auth.AuthProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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

    @NotNull
    private AuthProvider provider;

    @NotNull
    private Gender gender;

    private String providerId;

    private String courseOfStudy;

    private String department;

    private String faculty;

    private String nameOfSchool;

    private String yearOfEntry;

    private String level;

    private String cgpa;

    private String phoneNumber;

    private boolean deleted=false;

    LocalDateTime timeCreated;

    LocalDateTime timeUpdated;

    LocalDateTime lastLogin;

}
