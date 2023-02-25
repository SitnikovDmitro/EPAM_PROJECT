package app.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Servlet context listener
 **/
public class AppServletContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(AppServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("Library application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("Library application stopped");
    }
}