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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.config.MuleProperties;
import org.mule.extras.client.MuleClient;
import org.mule.umo.UMOMessage;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Invoker to make a synchronous call to a Mule Server
 */
public class SynchClientInvokerInterceptor implements MethodInterceptor, InitializingBean {

    protected transient Log logger = LogFactory.getLog(getClass());

    private String endpointAddress;
    private MuleClient client;  //TODO Should MuleClient be used?

    // private AbstractTransformer requestTransformer;
    // private AbstractTransformer responseTransformer;

    /**
     * Invoke method on Mule service defined by the supplied endpointAddress and called method
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (logger.isDebugEnabled()) {
            logger.debug("invocation=" + invocation);
        }

        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();

        Map<String, String> props = new HashMap<String, String>();
        props.put(MuleProperties.MULE_METHOD_PROPERTY, method.getName());

        UMOMessage msg = client.send(endpointAddress, args, props);

        // TODO use responseTransformer
        return msg.getPayload();
    }

    public void afterPropertiesSet() throws java.lang.Exception {
        if (this.endpointAddress == null) {
            throw new IllegalArgumentException("endpointAddress is required");
        }

        // Is this necessary? TestCase fails if run together with other TestCases
        //UMOManagementContext ctx = MuleServer.getManagementContext();
        //client = new MuleClient(ctx != null ? ctx : null);
        client = new MuleClient();
    }

    public String getEndpointAddress() {
        return endpointAddress;
    }

    public void setEndpointAddress(String endpointAddress) {
        this.endpointAddress = endpointAddress;
    }

    public MuleClient getClient() {
        return client;
    }

    public void setClient(MuleClient client) {
        this.client = client;
    }
}
