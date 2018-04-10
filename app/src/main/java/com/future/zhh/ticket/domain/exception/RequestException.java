package com.future.zhh.ticket.domain.exception;

/**
 * Created by Administrator on 2017/11/19.
 */


public class RequestException extends Throwable {

    public RequestException() {
        super();
    }

    public RequestException(final String message) {
        super(message);
    }

    public RequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RequestException(final Throwable cause) {
        super(cause);
    }

}
