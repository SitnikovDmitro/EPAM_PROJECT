package app.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Session listener
 **/
public class AppSessionListener implements HttpSessionListener {
    private static final Logger logger = LogManager.getLogger(AppSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.info("Session created : session id = "+event.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        logger.info("Session destroyed : session id = "+event.getSession().getId());
    }
}
