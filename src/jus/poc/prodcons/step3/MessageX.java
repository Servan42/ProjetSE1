package jus.poc.prodcons.step3;

import java.util.Date;

import jus.poc.prodcons.Message;

public class MessageX implements Message {
	int id;
	int nb;
	long Tput;
	
	public MessageX(int id, int nb) {
		this.id = id;
		this.nb = nb;
	}
	
	public String toString() {
		return "Message " + nb + " from producteur " + id + " at time " + Tput;
	}
	
	public void setTime() {
		Tput = new Date().getTime();
	}
	
	public long getTime() {
		return Tput;
	}
	
	public int getId() {
		return id;
	}
	
	public int getNb() {
		return nb;
	}
}
