package jus.poc.prodcons.step3;

import java.util.Date;

import jus.poc.prodcons.Message;

/**
 * Classe contenant les inforamtions relatives au message produit par un
 * producteur, et son format de sortie.
 * 
 * @author CHANET CHARLOT
 *
 */
public class MessageX implements Message {
	private int id;
	private int nb;
	private long Tput;

	/**
	 * Constructeur de MessageX
	 * 
	 * @param id
	 *            Identifiant du producteur ayant creé le message
	 * @param nb
	 *            Numero du message
	 */
	public MessageX(int id, int nb) {
		this.id = id;
		this.nb = nb;
	}

	/**
	 * Methode qui permet de representer le message et ses informations sous
	 * forme textuelle.
	 * 
	 * @return Une chaine de caractères contenant le message, son numero, le
	 *         producteur qui l'a créé et à quel moment.
	 */
	public String toString() {
		return "Message " + nb + " from producteur " + id + " at time " + Tput;
	}

	/**
	 * Methode qui stocke dans un attribut de la classe à quel moment cette
	 * methode à été appelé (grace à Date).
	 */
	public void setTime() {
		Tput = new Date().getTime();
	}

	/**
	 * Methode qui permet de recuperer l'indicateur temporel ou a été appelé la
	 * methode MessageX.setTime().
	 * 
	 * @return Un long correspondant au temps.
	 */
	public long getTime() {
		return Tput;
	}
}
