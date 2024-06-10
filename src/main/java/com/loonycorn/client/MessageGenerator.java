package com.loonycorn.client;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class MessageGenerator {

    public static void main(String[] args) throws NamingException, JMSException {

        final Hashtable<String,String> jndiProperties = new Hashtable<>();


        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        Context context = new InitialContext(jndiProperties);

        ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
        System.out.println("Found connection factory jms/RemoteConnectionFactory in JNDI");
        Destination dest = (Destination) context.lookup("jms/queue/LoonyQueue");

        System.out.println("Found destination queue/MDBQueue in JNDI");
        Connection connection = cf.createConnection("messagingapp","vicky");

        Session session = connection.createSession(
                false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer publisher = session.createProducer(dest);

        connection.start();

        TextMessage message = session.createTextMessage("Hello message consumer!");
        publisher.send(message);

        message = session.createTextMessage("Hello again!");
        publisher.send(message);

        message = session.createTextMessage("Just thought I'd check in for a second time.");
        publisher.send(message);

        message = session.createTextMessage("Goodbye.");
        publisher.send(message);

        connection.close();

    }
}
