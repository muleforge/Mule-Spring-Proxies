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

import org.mule.extras.springproxies.testmodel.AService;
import org.mule.extras.springproxies.testmodel.Anything;
import org.mule.extras.springproxies.testmodel.Something;
import org.mule.tck.FunctionalTestCase;
import org.mule.umo.UMOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestSynchClientFactoryBean extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "synchclient-mule-config.xml";
    }

    @Override
    protected void doSetUp() throws Exception {
        org.apache.log4j.BasicConfigurator.configure();
        super.doSetUp();
    }

    public void testMethodsUsingVM() throws UMOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("synchclient-client-config.xml");

        AService service = (AService) ctx.getBean("testServiceVM");

        // methodInteger(Integer i)
        {
            Integer result = service.methodInteger(9999);
            assertEquals(9999, result.intValue());
        }

        // methodStr(String str)
        {
            String result = service.methodStr("Karl-Johan");
            assertEquals("Karl-Johan_1", result);
        }

        // methodStr(String str1, String str2)
        {
            String result = service.methodStr("Karl", "Johan");
            assertEquals("Karl_1_Johan_2", result);
        }

        // methodStr(String str1, String str2, String str3)
        {
            String result = service.methodStr("Karl", "Johan", "Linus");
            assertEquals("Karl_1_Johan_2_Linus_3", result);
        }

        // methodStr2(String str)
        {
            String result = service.methodStr2("Linus");
            assertEquals("Linus_2", result);
        }

        // Anything aMethod(Something s)
        {
            Anything result = service.aMethod(new Something("nothing"));
            assertNotNull(result);
            assertEquals("Result from AServiceImpl, recived: 'nothing'", result.getMsg());
            assertEquals(123456.999, result.getValue(), 0.0001);
        }
    }
}
