package org.mule.extras.springproxies;

import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.mule.config.MuleProperties;
import org.mule.extras.client.MuleClient;
import org.mule.extras.springproxies.testmodel.AService;
import org.mule.impl.MuleMessage;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;
import org.springframework.aop.framework.ProxyFactory;

import java.util.HashMap;
import java.util.Map;

public class SynchClientInvokerInterceptorTestCase extends TestCase {

    public void testInvokeOk() throws UMOException {
        Mockery context = new Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };

        // --- Data ---
        final String endpointAddress = "my://somepath?withquery=q";
        final String method = "methodStr";
        final String arg = "aValue";
        final Object[] args = new Object[]{arg};
        final Map<String, String> props = new HashMap<String, String>();
        final String retVal = "aRetValue";
        final UMOMessage msg = new MuleMessage(retVal);

        // -- Create collaborators --
        SynchClientInvokerInterceptor interceptor = new SynchClientInvokerInterceptor();
        final MuleClient muleClient = context.mock(MuleClient.class);

        // -- Wire dependencies --
        interceptor.setEndpointAddress(endpointAddress);
        interceptor.setClient(muleClient);
        ProxyFactory pf = new ProxyFactory(new Class[]{AService.class});
        pf.addAdvice(interceptor);
        AService target = (AService) pf.getProxy();

        // -- Expectations --
        props.put(MuleProperties.MULE_METHOD_PROPERTY, method);
        context.checking(
                new Expectations() {
                    {
                        one(muleClient).send(with(equal(endpointAddress)), with(equal(args)), with(equal(props)));
                        will(returnValue(msg));
                    }
                }
        );

        // -- Execute --
        String result = target.methodStr(arg);

        // -- Verify --
        assertEquals(retVal, result);
        context.assertIsSatisfied();
    }

    public void testAfterPropertiesSet() {
        // Missing endpointAdress
        {
            SynchClientInvokerInterceptor interceptor = new SynchClientInvokerInterceptor();

            try {
                interceptor.afterPropertiesSet();
                fail("IllegalArgumentException should have been thrown");
            } catch (Exception e) {
                assertTrue(e instanceof IllegalArgumentException);
                assertEquals("endpointAddress is required", e.getMessage());
            }
        }

        // Ok
        {
            SynchClientInvokerInterceptor interceptor = new SynchClientInvokerInterceptor();
            interceptor.setEndpointAddress("address");
            try {
                interceptor.afterPropertiesSet();
            } catch (Exception e) {
                fail("No Exception should have been thrown, message=" + e.getMessage());
            }
        }
    }

}
