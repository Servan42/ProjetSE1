package jus.poc.prodcons.step4;

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
	private int nbExmplrMax;
	private int nbRemain;
	private long Tput;

	/**
	 * Constructeur de MessageX
	 * 
	 * @param id
	 *            Identifiant du producteur ayant creé le message
	 * @param nb
	 *            Numero du message
	 * @param nbExmplr
	 *            En combien d'exemplaires le message doit il être produit.
	 */
	public MessageX(int id, int nb, int nbExmplr) {
		this.id = id;
		this.nb = nb;
		this.nbExmplrMax = nbExmplr;
		this.nbRemain = nbExmplr;
	}

	/**
	 * Methode qui permet de representer le message et ses informations sous
	 * forme textuelle.
	 * 
	 * @return Une chaine de caractères contenant le message, son numero, le
	 *         producteur qui l'a créé et à quel moment, et le nombre de copies
	 *         du message restantes.
	 */
	public String toString() {
		return "Message " + nb + " from producteur " + id + " at time " + Tput + "\n\t" + nbRemain + " remaining";
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

	/**
	 * Methode permettant de décrémenter le nombre de copies restantes du
	 * message.
	 */
	public void conso() {
		nbRemain--;
	}

	/**
	 * Methode permettant de recuperer le nombre n'exemplaires que possede le
	 * message lors de sa production
	 * 
	 * @return Le nombre d'exemplaires
	 */
	public int Exemplaires() {
		return nbExmplrMax;
	}

	/**
	 * Methode permettant de savoir si il reste des exemplaires du message
	 * disponible.
	 * 
	 * @return TRUE si il n'y a plus d'exemplaires disponibles, FALSE sinon.
	 */
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
