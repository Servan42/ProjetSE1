package jus.poc.prodcons.step1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

/**
 * Tableau de garde-action 
 *	Methode | Garde 					| Action
 *	--------|---------------------------|------------------
 *	get 	| buffer.length > 0 		| return message, vider cette case du buffer
 *	put 	| buffer.length < buffsize 	| remplir une case du buffer
 */

public class ProdCons implements Tampon {
	
	private int buffSize;
	private MessageX[] buffer;
	
	public ProdCons(int size){
		buffSize = size;
		buffer = new MessageX[buffSize];
	}

	
	/**
	 * @return Nombre de message en attente de consommation (nombre de cases utilisées dans le tableau) 
	 */
	@Override
	public int enAttente() {
		int messages = 0;
		for (int i = 0; i < buffSize; i++) {
			if(buffer[i] != null) messages++;
		}
		return messages;
	}

	@Override
	public synchronized Message get(_Consommateur arg0) throws Exception, InterruptedException {
		long minTime = Long.MAX_VALUE;
		int minId = 0;
		MessageX retour;
		while (!(enAttente() > 0)) {
			try {
				wait();
			} catch (Exception e) {
				System.out.println("Fonction get " + e.toString() + " consommateur " + arg0.identification());
			}
		}
		for(int i = 0; i < buffSize; i++){
			if(buffer[i] != null && buffer[i].getTime() < minTime){
				minTime = buffer[i].getTime();
				minId = i;
			}
		}
		retour = buffer[minId];
		buffer[minId] = null;
		notifyAll();
		return retour;
	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		int i = 0;
		while(!(enAttente() < buffSize)) 
			try{
				wait();
			} catch(Exception e) {
				System.out.println("Fonction put " + e.toString() + " producteur " + arg0.identification());
			}
			
		while(i<buffSize && buffer[i] != null) i++;
			
		if(i<buffSize) { 
			((MessageX)arg1).setTime();
			System.out.println(arg1.toString());
			buffer[i] = (MessageX)arg1;
		} else throw new Exception("Tentative de put buffer plein");
		notifyAll();
	}

	@Override
	public int taille() {
		return buffSize;
	}

}
