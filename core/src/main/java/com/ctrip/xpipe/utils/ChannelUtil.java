package com.ctrip.xpipe.utils;


import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;

/**
 * @author marsqing
 *
 *         Jul 28, 2016 2:59:57 PM
 */
public class ChannelUtil {

	private final static Logger logger = LoggerFactory.getLogger(ChannelUtil.class);

	public static String getRemoteIpLocalPort(Channel channel) {
		String remoteIpLocalPort = "unknown";

		try {
			String remoteIp = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
			int localPort = ((InetSocketAddress) channel.localAddress()).getPort();
			return remoteIp + "->" + localPort;
		} catch (Exception e) {
			logger.warn("Error parse remote ip and local port from Channel {}", channel);
		}

		return remoteIpLocalPort;
	}

	public static String getRemoteAddr(Channel channel) {
		
		String remoteIpLocalPort = "unknown";
		try {
			SocketAddress remoteAddr = channel.remoteAddress();
			return String.format("R(%s)", getSimpleIpport(remoteAddr));
		} catch (Exception e) {
			logger.warn("Error parse remote ip and local port from Channel {}", channel);
		}
		return remoteIpLocalPort;
	}

	private static String getSimpleIpport(SocketAddress remoteAddr) {
		
		if(remoteAddr == null){
			return null;
		}
		
		if(remoteAddr instanceof InetSocketAddress){
			
			InetSocketAddress addr = (InetSocketAddress) remoteAddr;
			return String.format("%s:%d", addr.getHostString(), addr.getPort());
		}
		return remoteAddr.toString();
	}

	public static String getDesc(Channel channel){
		
		if(channel == null){
			return null;
		}
		return String.format("L(%s)->R(%s)", getSimpleIpport(channel.localAddress()), getSimpleIpport(channel.remoteAddress()));
		
	}

}
