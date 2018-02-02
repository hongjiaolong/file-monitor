/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor.handler;

/**
 *
 *
 * @author Gan
 * @date 2018年2月2日 下午2:40:01
 * @version 1.0
 *
 */
public class HandleException extends Exception {

    static final long serialVersionUID = -8114332309296435787L;

    public HandleException() {
        super();
    }

    public HandleException(String message) {
        super(message);
    }

    public HandleException(Throwable cause) {
        super(cause);
    }

    public HandleException(String message, Throwable cause) {
        super(message, cause);
    }

}
