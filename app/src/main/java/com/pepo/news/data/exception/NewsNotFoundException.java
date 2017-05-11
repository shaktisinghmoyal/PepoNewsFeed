
package com.pepo.news.data.exception;

/**
 * Exception throw by the application when a News search can't return a valid result.
 */
public class NewsNotFoundException extends Exception {
    public NewsNotFoundException() {
        super();
    }

    public NewsNotFoundException(final String message) {
        super(message);
    }

    public NewsNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NewsNotFoundException(final Throwable cause) {
        super(cause);
    }
}
