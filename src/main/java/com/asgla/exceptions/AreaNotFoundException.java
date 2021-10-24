package com.asgla.exceptions;

public class AreaNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>AreaNotFoundException</code> without
     * detail message.
     */
    public AreaNotFoundException() {
    }

    /**
     * Constructs an instance of <code>AreaNotFoundException</code> with the
     * specified detail message.
     * <p>
     *
     * @param msg the detail message.
     */
    public AreaNotFoundException(String msg) {
        super(msg);
    }

}
