package com.venturebridge.cdw;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create server socket for listening cross domain confirm client.
 * Flash socket client connect automatically cross domain server.
 * Send the text of "crossdomain.xml" to flash socket client.
 * The "crossdomain.xml" place in parent project's webapp directory.
 * 
 * @author coozplz
 */
public class CrossDomainListener extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(CrossDomainListener.class);

	/**
	 * Socket for listen flash socket client.
	 */
	ServerSocket serverSocket;
	
	/**
	 * Crossdomain listen port. 
	 * Flash object automatically connect to cross domain server on port 843.
	 * So, we are listening on port 843.
	 */
	int port = 843;
	
	
	
	/**
	 * Xml formmated text.
	 * This text will send to flash object.
	 */
	String domainText;
	
	/**
	 * Flag to run or not.
	 */
	boolean isRun = true;

	/**
	 * Constructor for listener
	 * @param domainText	CrossDomainText
	 * @param port	Port for binding server.
	 */
	protected CrossDomainListener(String domainText, int port) {
		this.domainText = domainText;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			while (isRun) {
				Socket s = serverSocket.accept();
				SocketAddress clientIp = s.getRemoteSocketAddress();
				logger.debug("Connected client: {}", clientIp);
				try (OutputStream os = s.getOutputStream();
						OutputStreamWriter osw = new OutputStreamWriter(os);
						BufferedWriter bw = new BufferedWriter(osw);) {
					bw.write(domainText);
					bw.flush();
					Thread.sleep(100);
					s.close();
				} catch (IOException e) {
					logger.warn(
							"can't process sending crossdomain text, {}", clientIp, e);
				} catch (InterruptedException ie) {
					logger.warn("server is interrupted.");
				} finally {
					IOUtils.closeQuietly(s);
				}
			}
		} catch (IOException e) {
			logger.warn("Exception in binding, port={}", port, e);
		}
	}

	/**
	 * Stop the server.
	 * @throws IOException When closing the ServerSocket. 
	 */
	protected void stopServer() throws IOException {
		try {
			isRun = false;
			serverSocket.close();
		} catch (IOException e) {
			throw new IOException("Can't stop the crossdomain server");
		}
	}
}
