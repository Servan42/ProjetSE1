package jus.poc.prodcons.step1;

import java.util.ArrayList;

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

	@Override
	public int enAttente() {
		return buffer.length;
	}

	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {

		return null;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		synchronized(this) {
			int i = 0;
			while(!(buffer.length < buffSize)) 
				try{
					wait();
				} catch(Exception e) {
					System.out.println("Fonction get " + e.toString());
				}
			((MessageX)arg1).setTime();
			while(i<buffSize && buffer[i] == null) i++;
			if(i<buffSize) buffer[i] = (MessageX)arg1;
			else throw new Exception("Tentative de put buffer plein");
		}
	}

	@Override
	public int taille() {
		return buffSize;
	}

}
