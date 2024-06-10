package com.loonycorn.client;

import com.loonycorn.server.TimerBean;
import com.loonycorn.server.TimerInterfaceRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.text.SimpleDateFormat;
import javax.ejb.ScheduleExpression;

public class TimerClient {
    public static void main(String[] args) throws Exception {

        invokeStatelessBean();

    }

    private static void invokeStatelessBean() throws NamingException, ParseException {

        TimerInterfaceRemote timerBean = lookupRemoteStatelessCounter();
        System.out.println("Obtained a remote timer bean for invocation");

        System.out.println("About to submit a timed job at: [" + (new Date()).toString() + "]" );

        //timerBean.createTimer(20000);
/*
        SimpleDateFormat formatter =
                new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm");
        Date date = formatter.parse("06/10/2024 at 15:43");
        timerBean.createTimer(date);
*/
        ScheduleExpression schedule = new ScheduleExpression();
        schedule.dayOfWeek("Thu");
        schedule.hour("11-12, 18");
        schedule.minute("*/2");
        schedule.second("30");
        timerBean.createCalendarTimer(schedule);

        System.out.println("Client program has ended at: [" + (new Date()).toString() + "]");

    }

    private static TimerInterfaceRemote lookupRemoteStatelessCounter() throws NamingException {

        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        final Context context = new InitialContext(jndiProperties);

        final String appName = "";
        final String moduleName = "ejb-intro-1.0";
        final String distinctName = "";
        final String beanName = TimerBean.class.getSimpleName();
        final String viewClassName = TimerInterfaceRemote.class.getName();

        return (TimerInterfaceRemote) context.lookup("ejb:" + appName + "/"
                + moduleName + "/" + distinctName + "/"
                + beanName + "!" + viewClassName);
    }
}
