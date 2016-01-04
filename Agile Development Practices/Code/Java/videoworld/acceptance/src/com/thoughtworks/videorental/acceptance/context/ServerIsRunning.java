package com.thoughtworks.videorental.acceptance.context;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;


import net.sf.sahi.client.Browser;

public class ServerIsRunning {
	
	private static final String WEBAPP_PATH = System.getProperty("webapp.path", "/Users/lminudel/Code/videoworld-tmp/src/main");

	private JettyServer jettyServer;

	public ServerIsRunning(Browser browser) {
	}

	public void setUp() throws Exception {
		jettyServer = new JettyServer(WEBAPP_PATH, 8081, "/");		
		jettyServer.start();
	}

	public void tearDown() throws Exception {
		jettyServer.stop();
	}

	public final class JettyServer {
		private final Server server;
		private final String webbapp;
		private final String context;
	
		public JettyServer(final String webbapp, final int port, final String context) throws Exception {
			this.webbapp = webbapp;
			this.context = context;
			try {
				server = new Server(port);
			} catch (Exception e) {
				e.printStackTrace();
				throw(e);
			}			
		}
	
		public void start() throws Exception {
			final WebAppContext webapp = new WebAppContext(webbapp, context);
			server.addHandler(webapp);
			try {
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	
		public void stop() throws Exception {
			server.stop();
		}
	
		public void join() throws Exception {
			server.join();
		}
		
	}
}