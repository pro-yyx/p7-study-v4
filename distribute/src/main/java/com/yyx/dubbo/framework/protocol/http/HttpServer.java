package com.yyx.dubbo.framework.protocol.http;

import org.apache.catalina.Engine;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.parser.Host;

/**
 * @author yinyuxin
 * @description
 * @date 2021/9/14 17:18
 */
public class HttpServer {

    public void start(String hostName, Integer port) {
        // tomcat /  jetty 通过配置来决定
        Tomcat tomcat = new Tomcat();
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");

        Connector connector = new Connector();
        connector.setPort(port);

        Engine engine=new StandardEngine();
        engine.setDefaultHost(hostName);

        StandardHost standardHost = new StandardHost();
        standardHost.setName(hostName);

        String contextPath = "";
        StandardContext standardContext = new StandardContext();
        standardContext.setPath(contextPath);
        standardContext.addLifecycleListener(new Tomcat.FixContextListener());

        standardHost.addChild(standardContext);
        engine.addChild(standardHost);

        service.setContainer(engine);
        service.addConnector(connector);
        tomcat.addServlet(contextPath, "dispatcher", new DispatcherServlet());
        standardContext.addServletMappingDecoded("/*","dispatcher");

        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();

        }

    }
}
