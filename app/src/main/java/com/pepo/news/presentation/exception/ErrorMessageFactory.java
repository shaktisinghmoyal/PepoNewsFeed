
package com.pepo.news.presentation.exception;

import android.content.Context;

import com.pepo.news.R;
import com.pepo.news.data.exception.NetworkConnectionException;
import com.pepo.news.data.exception.NewsNotFoundException;


/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

    private final String Tag = "ErrorMessageFactory";
    private ErrorMessageFactory() {
        //empty
    }

    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);

        if (exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection);
        } else if (exception instanceof NewsNotFoundException) {
            message = context.getString(R.string.exception_message_news_not_found);
        }

        return message;
    }
}
