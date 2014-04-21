package com.wselwood.mpcreader;

/**
 * Exception to be used when data in the file is invalid.
 *
 * Created by wselwood on 14/04/14.
 */
public class InvalidDataException extends Exception {

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
