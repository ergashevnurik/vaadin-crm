package com.vaadin.crm.backend.exception;

import org.omg.PortableServer.THREAD_POLICY_ID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
