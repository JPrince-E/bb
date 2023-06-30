package africa.breej.africa.breej.exception;

import org.springframework.http.HttpStatus;

public class WebApiException extends RuntimeException{

    private final HttpStatus httpStatus;

    private final boolean fatal;

    public WebApiException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.fatal = false;
    }

    public WebApiException(final String message, final HttpStatus httpStatus, final boolean fatal) {
        super(message);
        this.httpStatus = httpStatus;
        this.fatal = fatal;
    }

    public WebApiException(final String message, final Throwable cause, final HttpStatus httpStatus) {
        super(message, cause );
        this.httpStatus = httpStatus;
        this.fatal = false;
    }

    public WebApiException(final String message, final Throwable cause, final HttpStatus httpStatus, final boolean fatal) {
        super(message, cause );
        this.httpStatus = httpStatus;
        this.fatal = fatal;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public boolean isFatal() {
        return fatal;
    }
}
