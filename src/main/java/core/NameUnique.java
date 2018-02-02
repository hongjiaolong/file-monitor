/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package core;

/**
 *
 *
 * @author Gan
 * @date 2018年2月2日 上午10:36:39
 * @version 1.0
 *
 */
public interface NameUnique {
    String generateName();
    boolean exists(String name);
    void cancel(String name);
}
