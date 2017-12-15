package jus.poc.prodcons.step4;

import java.util.Date;

import jus.poc.prodcons.Message;

public class MessageX implements Message {
	int id;
	int nb;
	int nbExmplr;
	long Tput;
	
	public MessageX(int id, int nb, int nbExmplr) {
		this.id = id;
		this.nb = nb;
		this.nbExmplr = nbExmplr;
	}
	
	public String toString() {
		return "Message " + nb + " from producteur " + id + " at time " + Tput + "\n\t" + nbExmplr + " remaining";
	}
	
	public void setTime() {
		Tput = new Date().getTime();
	}
	
	public long getTime() {
		return Tput;
	}
	
	public void conso() {
		nbExmplr--;
	}
	
	public int Exmeplaires() {
		return nbExmplr;
	}
	
	public boolean estVide() {
		return nbExmplr <= 0;
	}
}
