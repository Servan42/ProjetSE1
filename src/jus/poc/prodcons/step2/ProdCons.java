package jus.poc.prodcons.step2;

import java.util.concurrent.Semaphore;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.step2.MessageX;

/*
 * Tableau de garde-action 
 *	Methode | Garde 					| Action
 *	--------|---------------------------|------------------
 *	get 	| buffer.length > 0 		| return message, vider cette case du buffer
 *	put 	| buffer.length < buffsize 	| remplir une case du buffer
 */

/**
 * Classe qui gère les stockages et destockages du tampon
 * 
 * @author CHANET CHARLOT
 *
 */
public class ProdCons implements Tampon {

	private Semaphore put = new Semaphore(1);
	private Semaphore get = new Semaphore(1);
	private int buffSize;
	private MessageX[] buffer;

	/**
	 * Constructeur de ProdCons
	 * 
	 * @param size
	 *            Nombre de places à allouer dans le buffer.
	 */
	public ProdCons(int size) {
		buffSize = size;
		buffer = new MessageX[buffSize];
	}

	/**
	 * @return Nombre de message en attente de consommation (nombre de cases
	 *         utilisées dans le tableau)
	 */
	public int enAttente() {
		int messages = 0;
		for (int i = 0; i < buffSize; i++) {
			if (buffer[i] != null)
				messages++;
		}
		return messages;
	}

	/**
	 * Methode permettant au Consommateur de recuperer un message dans le
	 * tampon. Recupère le message le plus ancien du tampon, et met la case du
	 * tampon à <code>null</code> après recuperation
	 * 
	 * @param arg0
	 *            Consommateur qui demande le message
	 * @return Message sorti du tampon.
	 */
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		get.acquire();
		long minTime = Long.MAX_VALUE;
		int minId = 0;
		MessageX retour;
		synchronized (this) {
			while (!(enAttente() > 0)) {
				try {
					wait();
				} catch (Exception e) {
					System.out.println("Fonction get " + e.toString() + " consommateur " + arg0.identification());
				}
			}
			for (int i = 0; i < buffSize; i++) {
				if (buffer[i] != null && buffer[i].getTime() < minTime) {
					minTime = buffer[i].getTime();
					minId = i;
				}
			}
			retour = buffer[minId];
			buffer[minId] = null;
			notifyAll();
		}
		get.release();
		return retour;
	}

	/**
	 * Methode permettant au Producteur de poser un message dans le tampon, à un
	 * emplacement vide (=null), et seulement si le tampon n'est pas plein.
	 * 
	 * @param arg0
	 *            Producteur qui pose le message
	 * @param arg1
	 *            Message à deposer dans le tampon
	 */
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		put.acquire();
		int i = 0;
		synchronized (this) {
			while (!(enAttente() < buffSize))
				try {
					wait();
				} catch (Exception e) {
					System.out.println("Fonction put " + e.toString() + " producteur " + arg0.identification());
				}

			while (i < buffSize && buffer[i] != null)
				i++;

			if (i < buffSize) {
				((MessageX) arg1).setTime();
				System.out.println(arg1.toString());
				buffer[i] = (MessageX) arg1;
			} else
				throw new Exception("Tentative de put buffer plein");
			notifyAll();
		}
		put.release();
	}

	/**
	 * Getter de buffSize
	 * 
	 * @return Taille du buffer
	 */
	public int taille() {
		return buffSize;
	}

}
