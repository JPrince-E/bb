package africa.breej.africa.breej.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{

    private static final long serialVersionUID = 7395803808473928415L;

    public ConflictException(final String message) {
        super(message);
    }

}
