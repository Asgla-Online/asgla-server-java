package com.asgla.requests;

import com.asgla.requests.unity.Default;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.PrivilegedAction;
import java.util.Properties;

public class RequestFactory {

    public static final RequestFactory REQUESTS = new RequestFactory("requests.properties");
    private static final Logger log = LoggerFactory.getLogger(RequestFactory.class);
    private final Properties requests = new Properties();

    private RequestFactory(String propertiesFile) {
        try (InputStream in = RequestFactory.class.getResourceAsStream(propertiesFile)) {
            requests.load(in);
        } catch (IOException ex) {
            log.error("Unable to load request.properties", ex);
        }
    }

    public IRequest create(String command) {
        //RequestClassLoader requestLoader = AccessController.doPrivileged(
        //    new RequestLoaderAction()
        //);

        RequestClassLoader requestLoader = new RequestClassLoader(
            RequestFactory.class.getClassLoader());

        try {
            Class<IRequest> requestDefinition = (Class<IRequest>) requestLoader.loadClass(requests.getProperty(command, "com.asgla.requests.unity.DefaultRequest"));
            return requestDefinition.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            log.error("Error creating request", ex);
            return new Default();
        }
    }

    static class RequestLoaderAction implements
        PrivilegedAction<RequestClassLoader> {

        @Override
        public RequestClassLoader run() {
            return new RequestClassLoader(
                RequestFactory.class.getClassLoader());
        }
    }

}
