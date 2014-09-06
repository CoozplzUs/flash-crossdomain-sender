package com.venturebridge.cdw;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * This class will start CrossDomainServer.
 * @author coozplz
 *
 */
public class CrossDomainStarter {
	
	/**
	 * Listener for sending CrossDomainXmlText to client.
	 */
	CrossDomainListener listener;
	
	/**
	 * Xml text for sending.
	 */
	String crossDomainXmlText;
	
	/**
	 * Port for cross domain server.
	 */
	int port;
	
	/**
	 * Generate {@link CrossDomainStarter} object.
	 * @param crossDomainXmlText Xml formatted string of cross-domain-policy	
	 * @param port	Port for binding {@link CrossDomainListener}
	 */
	public CrossDomainStarter(String crossDomainXmlText, int port) {
		this.crossDomainXmlText = crossDomainXmlText;
		this.port = port;
	}
	
	/**
	 * 
	 * Start cross domain server.
	 * @throws IOException When fail on binding {@link ServerSocket}
	 * 
	 */
	public void startServer() throws IOException {
		try {
			if(listener != null) {
				listener.stopServer();
			}
			
			listener = new CrossDomainListener(crossDomainXmlText, port);
			listener.start();
		} catch (IOException e) {
			throw new IOException("can't start cross domain server ", e);
		}
	}
	
	/**
	 * Stop cross domain server.
	 */
	public boolean stopServer() {
		boolean result = true;
		if(listener != null) {
			try {
				listener.interrupt();
				listener.stopServer();
			} catch (IOException e) {
				result = false;
			}
		}
		return result;
	}
}
