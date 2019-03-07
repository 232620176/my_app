package com.hydra.core.fileservice.exception;

/**
 * 
 * @author wangshuang
 *
 */
public class FileException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -8099149920720403661L;

    /**
     * 
     */
    public FileException() {
        // do something();
    }

    /**
     * @param msg
     */
    public FileException(String msg) {
        super(msg);
    }

    /**
     * @param e
     */
    public FileException(Throwable e) {
        super(e);
    }

    /**
     * @param msg
     * @param e
     */
    public FileException(String msg, Throwable e) {
        super(msg, e);
    }
}
