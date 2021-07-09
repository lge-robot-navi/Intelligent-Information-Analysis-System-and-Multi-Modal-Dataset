package com.lge.mams.agentif.udperver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

// java.util.Date time=new java.util.Date((long)timeStamp*1000);
public class SeqImgHead {
	private int robotId; // 2
	private long timestamp; // 4
	private int ms; // 2
	private int locationCode; // 2
	private int payloadLength; // 2
	private byte totalNo; // 1
	private byte currNo; // 1
	private byte recordNo; // 1
	private byte reserved; // 1
	private String remoteIp;

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public void loadFrom(SeqImgHead h) {
		this.robotId = h.robotId;
		this.timestamp = h.timestamp;
		this.ms = h.ms;
		this.locationCode = h.locationCode;
		this.payloadLength = h.payloadLength;
		this.totalNo = h.totalNo;
		this.currNo = h.currNo;
		this.recordNo = h.recordNo;
		this.reserved = h.reserved;
		this.remoteIp = h.remoteIp;
	}

	public int getRobotId() {
		return robotId;
	}

	public void setRobotId(int robotId) {
		this.robotId = robotId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getMs() {
		return ms;
	}

	public void setMs(int ms) {
		this.ms = ms;
	}

	public int getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(int locationCode) {
		this.locationCode = locationCode;
	}

	public String getAreaCode() {
		if (this.locationCode == 1) return "G";
		return "P";
	}

	public int getPayloadLength() {
		return payloadLength;
	}

	public void setPayloadLength(int payloadLength) {
		this.payloadLength = payloadLength;
	}

	public byte getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(byte totalNo) {
		this.totalNo = totalNo;
	}

	public byte getCurrNo() {
		return currNo;
	}

	public void setCurrNo(byte currNo) {
		this.currNo = currNo;
	}

	public byte getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(byte recordNo) {
		this.recordNo = recordNo;
	}

	public byte getReserved() {
		return reserved;
	}

	public void setReserved(byte reserved) {
		this.reserved = reserved;
	}

	public Date getDate() {
		Date d = new Date(timestamp * 1000 + ms);
		return d;
	}

	public void setDate(Date d) {
		timestamp = (int) (d.getTime() / 1000);
		ms = (int) (d.getTime() % 1000);
	}

	public byte[] toBytes() {
		byte[] bytes = new byte[16];
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		// bb.order(ByteOrder.BIG_ENDIAN);

		bb.putShort((short) robotId);
		bb.putInt((int) timestamp);
		bb.putShort((short) ms);
		bb.putShort((short) locationCode);
		bb.putShort((short) payloadLength);
		bb.put(totalNo);
		bb.put(currNo);
		bb.put(recordNo);
		bb.put(reserved);
		return bytes;
	}

	public boolean fromBytes(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);

		bb.order(ByteOrder.LITTLE_ENDIAN);
		// bb.order(ByteOrder.BIG_ENDIAN);
		robotId = bb.getShort() & 0xffff;
		timestamp = bb.getInt() & 0xffffffff;
		;
		ms = bb.getShort() & 0xffff;
		locationCode = bb.getShort() & 0xffff;
		payloadLength = bb.getShort() & 0xffff;
		totalNo = bb.get();
		currNo = bb.get();
		recordNo = bb.get();
		reserved = bb.get();
		return true;
	}

}
