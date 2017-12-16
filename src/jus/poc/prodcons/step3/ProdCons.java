package jus.poc.prodcons.step3;

import java.util.concurrent.Semaphore;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
import jus.poc.prodcons.step3.MessageX;

/**
 * Tableau de garde-action 
 *	Methode | Garde 					| Action
 *	--------|---------------------------|------------------
 *	get 	| buffer.length > 0 		| return message, vider cette case du buffer
 *	put 	| buffer.length < buffsize 	| remplir une case du buffer
 */

public class ProdCons implements Tampon {
	
	private Semaphore put = new Semaphore(1);
	private Semaphore get= new Semaphore(1);
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
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		get.acquire();
		long minTime = Long.MAX_VALUE;
		int minId = 0;
		MessageX retour;
		synchronized(this) {
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
		}
		get.release();
		return retour;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		put.acquire();
		int i = 0;
		synchronized(this) {
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
		put.release();
	}

	@Override
	public int taille() {
		return buffSize;
	}

}
