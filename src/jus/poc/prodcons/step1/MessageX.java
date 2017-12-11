package jus.poc.prodcons.step1;

import jus.poc.prodcons.Message;

public class MessageX implements Message {
	int id;
	int nb;
	
	public MessageX(int id, int nb) {
		this.id = id;
		this.nb = nb;
	}
	
	public String toString() {
		return "Message " + nb + " from producteur " + id;
	}
}
