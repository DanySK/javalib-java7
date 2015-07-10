/*
 * Copyright (C) 2010-2014, Danilo Pianini and contributors
 * listed in the project's pom.xml file.
 * 
 * This file is part of Alchemist, and is distributed under the terms of
 * the GNU General Public License, with a linking exception, as described
 * in the file LICENSE in the Alchemist distribution's top directory.
 */
package org.danilopianini.io;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * This is a collection of static network-related methods.
 * 
 * @author Danilo Pianini
 * 
 */
public final class NetworkUtilities {

	private NetworkUtilities() {
	}

	/**
	 * @return A list of all the IP addresses of the current machine
	 * @throws SocketException
	 *             in case of I/O Errors
	 */
	public static List<String> getLocalIPAddresses() throws SocketException {
		final ArrayList<String> iplist = new ArrayList<String>(1);
		final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			final NetworkInterface ni = interfaces.nextElement();
			final Enumeration<InetAddress> ipaddresses = ni.getInetAddresses();
			while (ipaddresses.hasMoreElements()) {
				final String ip = ipaddresses.nextElement().getHostAddress();
				if (ip.contains(".") && !ip.contains("127.0.0.")) {
					iplist.add(ip);
				}
			}
		}
		iplist.trimToSize();
		return iplist;
	}

}
