/**
 * Created by arthurgeron on 09/05/17.
 */

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class App implements Runnable {
    private Server jettyServer;

    public static void main(String[] args) throws Exception {
        App app = new App();

        app.jettyServer = app.getServerInstance();
        try {
            app.jettyServer.start();
            app.jettyServer.join();
        } catch (Exception e) {
            System.out.println("Exception! Stopping jetty server ");
            app.jettyServer.stop();
            e.printStackTrace();
        } finally {
            app.jettyServer.destroy();
        }
    }

    private Server getServerInstance() {
        if (jettyServer == null)
            newServerInstance();
        return jettyServer;
    }

    private void newServerInstance() {

        jettyServer = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(jettyServer, "/*");
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                API.class.getCanonicalName());

    }

    public void run() {

    }
}

