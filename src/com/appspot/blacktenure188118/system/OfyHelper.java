package com.appspot.blacktenure188118.system;

import com.appspot.blacktenure188118.entities.InstagramUser;
import com.appspot.blacktenure188118.entities.TelegramUser;
import com.googlecode.objectify.ObjectifyService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//
public class OfyHelper implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        // This will be invoked as part of a warmup request, or the first user
        // request if no warmup request was invoked.
        ObjectifyService.register(InstagramUser.class);
        ObjectifyService.register(TelegramUser.class);
    }

    public void contextDestroyed(ServletContextEvent event) {
        // App Engine does not currently invoke this method.
    }
}
