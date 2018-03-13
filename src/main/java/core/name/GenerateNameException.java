/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package core.name;

/**
 *
 *
 * @author Gan
 * @date 2018年2月2日 下午2:40:01
 * @version 1.0
 *
 */
public class GenerateNameException extends Exception {

    static final long serialVersionUID = -8438331969218334712L;

    public GenerateNameException() {
        super();
    }

    public GenerateNameException(String message) {
        super(message);
    }

    public GenerateNameException(Throwable cause) {
        super(cause);
    }

    public GenerateNameException(String message, Throwable cause) {
        super(message, cause);
    }

}
