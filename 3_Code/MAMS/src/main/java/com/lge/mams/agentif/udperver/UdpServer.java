package com.lge.mams.agentif.udperver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lge.mams.common.util.CiaConfig;

public class UdpServer extends Thread{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private DatagramSocket dsoc;
	private int port = 0;
	private SeqImageProvider imageProvider;
	private CiaConfig ciaConfig;

	public CiaConfig getCiaConfig() {
		return ciaConfig;
	}

	public void setCiaConfig(CiaConfig ciaConfig) {
		this.ciaConfig = ciaConfig;
	}

	public String getTimestamp() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss.SSS");
		return sdf.format(now);
	}

	public void startServer(int port, SeqImageProvider prov) {
		this.port = port;
		this.imageProvider = prov;
		start();

	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	@Override
	public void run() {
		try {
			System.out.println("START UdpServer");
			// 상대방이 연결할수 있도록 UDP 소켓 생성
			dsoc = new DatagramSocket(port);
			// 전송받은 데이터를 지정할 바이트 배열선언
			byte[] data = new byte[66536];
			byte[] header = new byte[16];
			SeqImgHead h = new SeqImgHead();

			// UDP 통신으로 전송을 받을 packet 객체생성
			DatagramPacket dp = new DatagramPacket(data, data.length);

			logger.info("데이터 수신 준비 완료....");
			while (true) {
				// 데이터 전송 받기
				dsoc.receive(dp);
				// 데이터 보낸곳 확인
				if(ciaConfig.isUdpLog()) logger.info(" 송신 IP : " + dp.getAddress());
				// 보낸 데이터를 Utf-8에 문자열로 벼환
				//String msg = new String(dp.getData(), "UTF-8");
				//System.out.println("보내 온 내용  : " + msg);
				
				System.arraycopy(data, 0, header, 0, header.length);
				h.fromBytes(header);
				
//				String imagename = getTimestamp() + ".jpg";
//				File file = new File(imagename);
//				FileOutputStream fos = new FileOutputStream(file);
//				fos.write(data,16,h.getPayloadLength());
//				fos.close();
//				System.out.println("filename : " + imagename);
				if(ciaConfig.isUdpLog()) logger.info("HEX:" + bytesToHex(header));
				
				if(ciaConfig.isUdpLog()) logger.info("RECV:" + ToStringBuilder.reflectionToString(h,ToStringStyle.MULTI_LINE_STYLE));
				imageProvider.add(h, data);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void stopServer() {
		if( dsoc != null ) {
			dsoc.close();
		}
	}
}
