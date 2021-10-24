package com.asgla.exceptions;

public class RoomCreationException extends Exception {

    /**
     * Creates a new instance of <code>CreateRoomException</code> without detail
     * message.
     */
    public RoomCreationException() {
    }

    /**
     * Constructs an instance of <code>CreateRoomException</code> with the
     * specified detail message.
     * <p>
     *
     * @param msg the detail message.
     */
    public RoomCreationException(String msg) {
        super(msg);
    }

}
