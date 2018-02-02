/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package com.gan.filemonitor;

/**
 *
 *
 * @author Gan
 * @date 2018年2月2日 下午2:40:01
 * @version 1.0
 *
 */
public class MonitorException extends Exception {

    static final long serialVersionUID = -4896360649187922137L;

    public MonitorException() {
        super();
    }

    public MonitorException(String message) {
        super(message);
    }

    public MonitorException(Throwable cause) {
        super(cause);
    }

    public MonitorException(String message, Throwable cause) {
        super(message, cause);
    }

}
