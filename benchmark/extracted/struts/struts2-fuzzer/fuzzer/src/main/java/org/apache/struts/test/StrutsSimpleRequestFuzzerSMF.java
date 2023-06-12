package org.apache.struts.test;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class StrutsSimpleRequestFuzzerSMF {

    private static String g_host = "localhost";

    private static int g_port = 8080;

    public static void main(String[] args) {
        new StrutsSimpleRequestFuzzer().runTest("hello.action");
    }

    public static void FuzzOne(String SMFData) {
        new StrutsSimpleRequestFuzzer().runTest(SMFData);
    }

    public void runTest(String fuzzyString) {
        Server server = null;
        try {
            server = new Server(g_port);
            WebAppContext strutsWebApp = new WebAppContext();
            strutsWebApp.setDescriptor("/WEB-INF/web.xml");
            File warFile = new File("struts2-webapp.war");
            strutsWebApp.setWar(warFile.getAbsolutePath());
            strutsWebApp.setContextPath("/struts-test");
            server.setHandler(strutsWebApp);
            do {
                try {
                    server.start();
                } catch (Exception ex) {
                }
            } while (!server.isStarted());
            HttpURLConnection http = (HttpURLConnection) new URL("http://" + g_host + ":" + g_port + "/struts-test/" + fuzzyString).openConnection();
            http.connect();
            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        } catch (IOException ex) {
        } catch (IllegalArgumentException ex) {
        } finally {
            if (server != null) {
                do {
                    try {
                        server.stop();
                    } catch (Exception ex) {
                    }
                } while (!server.isStopped());
            }
        }
    }
}
