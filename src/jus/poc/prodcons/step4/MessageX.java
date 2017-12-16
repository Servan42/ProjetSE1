package jus.poc.prodcons.step4;

import java.util.Date;

import jus.poc.prodcons.Message;

public class MessageX implements Message {
	int id;
	int nb;
	int nbExmplrMax;
	int nbRemain;
	long Tput;
	
	public MessageX(int id, int nb, int nbExmplr) {
		this.id = id;
		this.nb = nb;
		this.nbExmplrMax = nbExmplr;
		this.nbRemain = nbExmplr;
	}
	
	public String toString() {
		return "Message " + nb + " from producteur " + id + " at time " + Tput + "\n\t" + nbRemain + " remaining";
	}
	
	public void setTime() {
		Tput = new Date().getTime();
	}
	
	public long getTime() {
		return Tput;
	}
	
	public void conso() {
		nbRemain--;
	}
	
	public int Exemplaires() {
		return nbExmplrMax;
	}
	
	public boolean estVide() {
		return nbRemain <= 0;
	}
	
	public int getId() {
		return id;
	}
	
	public int getNb() {
		return nb;
	}
}
