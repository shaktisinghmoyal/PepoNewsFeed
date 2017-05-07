
package com.pepo.news.data.exception;

/**
 * Exception throw by the application when a Book search can't return a valid result.
 */
public class NewsNotFoundException extends Exception {
    private final String Tag = "BookNotFoundException";
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
