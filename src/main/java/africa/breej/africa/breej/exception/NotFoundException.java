package africa.breej.africa.breej.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends WebApiException{

    private static final long serialVersionUID = 7395803808473928415L;

    public NotFoundException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND );
    }
}
