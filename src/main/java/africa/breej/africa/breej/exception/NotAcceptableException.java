package africa.breej.africa.breej.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableException  extends WebApiException {

    private static final long serialVersionUID = 7395803808473928415L;


    public NotAcceptableException(final String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE);
    }

    public NotAcceptableException(final String message, final Throwable cause) {
        super(message, cause, HttpStatus.NOT_ACCEPTABLE );
    }
}
