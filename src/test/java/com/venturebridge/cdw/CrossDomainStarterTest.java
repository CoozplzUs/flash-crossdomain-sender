package com.venturebridge.cdw;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CrossDomainStarterTest {
	CrossDomainStarter worker;
	
	@Before
	public void setUp() throws Exception {
		InputStream in = getClass().getResourceAsStream("/crossdomain.xml");
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String readLine = "";
		StringBuffer buf = new StringBuffer();
		while((readLine = br.readLine()) != null) {
			buf.append(readLine);
		}
		worker = new CrossDomainStarter(buf.toString(), 1843);
//		System.out.println("setup");
	}

	@Test
	public void testStartServer() throws Exception {
		worker.startServer();
		Socket s = new Socket("127.0.0.1", 1843);
		InputStream in = s.getInputStream();
		StringBuffer sb = new StringBuffer();
		byte[] buffer = new byte[1024];
		while(true) {
			try {
				int readSize = in.read(buffer);
				sb.append(new String(buffer, 0, readSize));
			} catch (Exception e) {
				break;
			}
		}
		System.out.println(sb.toString());
		assertTrue(sb.toString().contains("cross-domain-policy"));
		
		worker.stopServer();
		
	}

	@Test
	public void testStopServer() throws Exception {
		Thread.sleep(2 * 1000);
		worker.startServer();
		assertTrue(worker.stopServer());
	}

}
