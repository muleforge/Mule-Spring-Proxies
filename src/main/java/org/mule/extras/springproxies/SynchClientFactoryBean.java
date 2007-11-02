/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extras.springproxies;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.ClassUtils;

public class SynchClientFactoryBean extends SynchClientInvokerInterceptor implements FactoryBean, BeanClassLoaderAware {
    private Object proxy;
    private Class serviceInterface;
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    public Object getObject() throws Exception {
        return this.proxy;
    }

    public Class getObjectType() {
        return this.serviceInterface;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();

        if (this.serviceInterface == null) {
            throw new IllegalArgumentException("businessInterface is required");
        }
        this.proxy = new ProxyFactory(this.serviceInterface, this).getProxy(this.beanClassLoader);
    }

    public Class getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class serviceInterface) {
        this.serviceInterface = serviceInterface;
    }
}
