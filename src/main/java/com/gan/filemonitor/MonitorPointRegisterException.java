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
public class MonitorPointRegisterException extends Exception {
    
    static final long serialVersionUID = 9143132574957493575L;

    public MonitorPointRegisterException() {
        super();
    }

    public MonitorPointRegisterException(String message) {
        super(message);
    }

    public MonitorPointRegisterException(Throwable cause) {
        super(cause);
    }

    public MonitorPointRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

}
