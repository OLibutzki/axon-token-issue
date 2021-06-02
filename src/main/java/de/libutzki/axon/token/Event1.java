package de.libutzki.axon.token;

public class Event1 {
	private String payload;
	
	public Event1() {
	}
	
	public Event1(String payload) {
		this.payload = payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public String getPayload() {
		return payload;
	}
}
