/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package core.lifecycle;

/**
 *
 *
 * @author Gan
 * @date 2018年2月5日 上午11:11:36
 * @version 1.0
 *
 */
public interface ILifeCycle {

    void init();
    void start();
    void stop();
    void destroy();

}
