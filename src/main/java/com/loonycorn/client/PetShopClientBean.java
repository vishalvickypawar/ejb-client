package com.loonycorn.client;

import com.loonycorn.server.PetShopBeanImplementation;
import com.loonycorn.server.PetShopBeanInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class PetShopClientBean {

    public static void main(String[] args) throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        final Context context = new InitialContext(jndiProperties);

        final String appName = "";
        final String moduleName = "ejb-intro-1.0";
        final String distinctName = "";
        final String beanName = PetShopBeanImplementation.class.getSimpleName();
        final String viewClassName = PetShopBeanInterface.class.getName();

        String jndi = "ejb:" + appName + "/"
                + moduleName + "/" + distinctName + "/"
                + beanName + "!" + viewClassName;

        PetShopBeanInterface petTypeBean = (PetShopBeanInterface) context.lookup(jndi);
        List<String> petTypes = petTypeBean.getBreeds("Dog");
        System.out.println("Dog breeds: " + petTypes);

        String[] catTypes = { "Siamese", "Persian", "Maine Coon", "Ragdoll", "Bengal" };

        petTypeBean.setBreeds(
                "Cat", Arrays.asList(catTypes));

        petTypes = petTypeBean.getBreeds("Cat");
        System.out.println("Cat breeds: " + petTypes);
    }
}
