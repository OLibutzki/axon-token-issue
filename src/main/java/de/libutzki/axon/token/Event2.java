package de.libutzki.axon.token;

public class Event2 {
	private String payload;
	
	public Event2() {
	}
	
	public Event2(String payload) {
		this.payload = payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public String getPayload() {
		return payload;
	}
}
