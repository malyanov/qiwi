package com.qiwi;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Malyanov Dmitry
 */
public class SiteListener implements ServletContextListener{
    private static final Logger log=LoggerFactory.getLogger(SiteListener.class);
    public static String appName="Qiwi";
    @Override
    public void contextInitialized(ServletContextEvent sce){
        log.info(appName+" started");
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce){
        log.info(appName+" shutting down...");
    }
}
