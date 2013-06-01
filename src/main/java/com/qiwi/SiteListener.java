package com.qiwi;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Malyanov Dmitry
 */
public class SiteListener implements ServletContextListener{
    public static String appName="Qiwi";
    @Override
    public void contextInitialized(ServletContextEvent sce){        
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce){        
    }
}
