package com.thoughtworks.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public final class JettyServer {
    private static final String USAGE = "Usage: jettyserver start/stop webapp-path wesite-port stop-port [context]\n";

    private final Server server;
    private final String webbApp;
    private final String context;

    public static void main(final String[] args) throws Exception {
        if (args.length < 4) {
            System.err.print(USAGE);
            System.exit(1);
        }

        final String operation = args[0];
        final String webApp = args[1];
        final int webAppPort = Integer.parseInt(args[2]);
        final int jettyStopMessagePort = Integer.parseInt(args[3]);
        final String context = (args.length < 5) ? "/" : args[4];


        if (operation.equals("start")) {
            final JettyServer jettyServer = new JettyServer(webApp, webAppPort, context);
            maybeStartMonitor(jettyServer, jettyStopMessagePort);

            jettyServer.start();
            jettyServer.join();
        }
        else if (jettyStopMessagePort > 0)  {
            Socket s = new Socket(InetAddress.getByName("127.0.0.1"), jettyStopMessagePort);
            OutputStream out = s.getOutputStream();
            System.out.println("*** sending jetty stop request");
            out.write(("\r\n").getBytes());
            out.flush();
            s.close();
        }

    }

    private static void maybeStartMonitor(final JettyServer jettyServer, int jettyStopMessagePort) {
        if (jettyStopMessagePort > 0) {
            new JettyMonitor(jettyStopMessagePort, jettyServer).start();
        }
    }

    public JettyServer(final String webbApp, final int port, final String context) {
        this.webbApp = webbApp;
        this.context = context;
        server = new Server(port);
    }

    public void start() throws Exception {
        final WebAppContext webAppContext = new WebAppContext(webbApp, context);
        server.addHandler(webAppContext);
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void join() throws Exception {
        server.join();
    }

    private static class JettyMonitor extends Thread {

        private final JettyServer jettyServer;
        private final ServerSocket socket;

        public JettyMonitor(final int stopPort, final JettyServer jettyServer) {
            this.jettyServer = jettyServer;
            setDaemon(true);
            setName("StopMonitor");
            try {
                this.socket = new ServerSocket(stopPort, 1, InetAddress.getByName("127.0.0.1"));
            } catch(final Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            System.out.println("*** running jetty 'stop' thread");
            Socket accept;
            try {
                accept = socket.accept();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                reader.readLine();
                System.out.println("*** stopping jetty embedded server");
                jettyServer.stop();
                accept.close();
                socket.close();
            } catch(final Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
