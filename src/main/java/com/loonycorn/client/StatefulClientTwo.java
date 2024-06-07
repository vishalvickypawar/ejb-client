package com.loonycorn.client;

import com.loonycorn.server.BeanIntroImplementation;
import com.loonycorn.server.BeanIntroInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class StatefulClientTwo {

    public static void main(String[] args) throws Exception {

        invokeStatefulBean();
    }

    private static void invokeStatefulBean() throws Exception {

        BeanIntroInterface statefulRemoteBean = lookupRemoteStatefulBean();
        System.out.println("Obtained a remote stateful bean for invocation");

        //statefulRemoteBean.setName("Stateful Client Two");
        String message = statefulRemoteBean.getMessage();
        System.out.println("Returned message: " + message);

    }
    private static BeanIntroInterface lookupRemoteStatefulBean() throws Exception {

        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        final Context context = new InitialContext(jndiProperties);

        final String appName = "";
        final String moduleName = "ejb-intro-1.0";
        final String distinctName = "";
        final String beanName = BeanIntroImplementation.class.getSimpleName();
        final String viewClassName = BeanIntroInterface.class.getName();

        return (BeanIntroInterface) context.lookup("ejb:" + appName + "/"
                + moduleName + "/" + distinctName + "/"
                + beanName + "!" + viewClassName
                + "?stateful");
    }

}
