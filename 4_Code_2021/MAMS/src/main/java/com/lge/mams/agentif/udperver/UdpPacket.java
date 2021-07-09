package com.lge.mams.agentif.udperver;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UdpPacket {

	private SeqImgHead head;
	private byte[] data;
	private boolean isValid = false;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Date date = new Date();
	
	public Date getDate() {
		return date;
	}
	
	public UdpPacket(SeqImgHead h, byte[] packet) {
		if( h.getPayloadLength() > 66536 || h.getPayloadLength() <= 16) {
			isValid = false;
			logger.error("error payload length : {}", h.getPayloadLength());
			return ;
		}
		head = new SeqImgHead();
		head.loadFrom(h);

		data = new byte[h.getPayloadLength()];
		System.arraycopy(packet, 16, data, 0, h.getPayloadLength());
		isValid = true;
	}

	public SeqImgHead getHead() {
		return head;
	}

	public void setHead(SeqImgHead head) {
		this.head = head;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
}
