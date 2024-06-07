package com.loonycorn.client;

import com.loonycorn.server.BeanIntroInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;


public class AccessBean
{
    public static void main( String[] args )
    {

        try{
            final Hashtable jndiProperties = new Hashtable();

            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,  "org.wildfly.naming.client.WildFlyInitialContextFactory");
            jndiProperties.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
            Context context = new InitialContext(jndiProperties);

            String appName = "";
            String moduleName = "ejb-intro-1.0";
            String distinctName = "";
            String beanName = "FirstBean";
            String interfaceName = BeanIntroInterface.class.getName();

            String name = String.format("ejb:%s/%s/%s/%s!%s",
                    appName, moduleName, distinctName, beanName, interfaceName);

            System.out.println(name);
            BeanIntroInterface bean = (BeanIntroInterface)context.lookup(name);
            String result = bean.getMessage();
            System.out.println("Result computed by EJB is : " + result);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
