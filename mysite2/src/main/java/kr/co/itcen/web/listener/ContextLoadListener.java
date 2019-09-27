package kr.co.itcen.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContextLoadListener implements ServletContextListener {

    public ContextLoadListener() {
    	
    }

    public void contextDestroyed(ServletContextEvent arg0)  {
    	
    }

    public void contextInitialized(ServletContextEvent servletContextEvent)  { 
    	String contextConfigLocation = servletContextEvent.getServletContext().getInitParameter("contextConfigLocation");
    	
    	System.out.println("MySite2 Application Starts..... " + contextConfigLocation);
    }
	
}
