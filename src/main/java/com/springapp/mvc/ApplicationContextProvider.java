package com.springapp.mvc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created by sirobaban on 28.07.2015.
 */
@Service(value = "applicationContextProvider")
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext springAppContext) throws BeansException {
        applicationContext = springAppContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
