package com.kh.spring.common.chatting;

public class Sdp {
	private String type;
	private String sdp;
	
	public Sdp() {
		// TODO Auto-generated constructor stub
	}

	public Sdp(String type, String sdp) {
		super();
		this.type = type;
		this.sdp = sdp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSdp() {
		return sdp;
	}

	public void setSdp(String sdp) {
		this.sdp = sdp;
	}
	
	
}
