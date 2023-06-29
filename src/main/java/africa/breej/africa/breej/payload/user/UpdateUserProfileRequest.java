package africa.breej.africa.breej.payload.user;

import africa.breej.africa.breej.model.user.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateUserProfileRequest {

    private String firstName;

    private String lastName;

    private String username;

    @Email
    private String email;

    private String courseOfStudy;

    private String department;

    private String faculty;

    private String nameOfSchool;

    private Gender gender;

    private String yearOfEntry;

    private String level;

    private String cgpa;

    private String phoneNumber;

}