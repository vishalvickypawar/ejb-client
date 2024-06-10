package com.loonycorn.client;

import com.loonycorn.EJBPersistenceBean;
import com.loonycorn.EJBPersistenceRemote;
import com.loonycorn.Employee;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.List;

public class PersistenceClient {

    public static void main(String[] args) throws NamingException {

        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        final Context context = new InitialContext(jndiProperties);

        final String appName = "";
        final String moduleName = "ejb-persistence-1.0";
        final String distinctName = "";
        final String beanName = EJBPersistenceBean.class.getSimpleName();
        final String viewClassName = EJBPersistenceRemote.class.getName();

        String jndi = "ejb:" + appName + "/"
                + moduleName + "/" + distinctName + "/"
                + beanName + "!" + viewClassName;

        EJBPersistenceRemote employeeBean = (EJBPersistenceRemote) context.lookup(jndi);

        Employee renee = new Employee(1234, "Renee Schneider",
                "Devops Engineer", 100000);
        employeeBean.addEmployee(renee);

        Employee matt = new Employee(1255, "Matt Foster",
                "Systems Engineer", 90000);
        employeeBean.addEmployee(matt);

        List<Employee> employeeList = employeeBean.getEmployees();

        System.out.println("Employees(s) entered so far: " + employeeList.size());

        int i = 0;

        for (Employee employee:employeeList) {
            System.out.println("\n" + (i+1) + ". Name: " + employee.getName());
            System.out.println("Position: " + employee.getPosition());
            System.out.println("Salary: $" + employee.getSalary());
            i++;
        }
    }
}
