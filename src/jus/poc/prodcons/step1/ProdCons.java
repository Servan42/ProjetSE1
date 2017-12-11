package jus.poc.prodcons.step1;

import java.util.ArrayList;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

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

	public ProdCons(int size) {
		buffSize = size;
		buffer = new MessageX[buffSize];
	}

	@Override
	public int enAttente() {
		return buffer.length;
	}

	@Override
	public synchronized Message get(_Consommateur arg0) throws Exception, InterruptedException {
		while(!(buffer.length > 0)){
			wait();
		}
		buffer.
		return null;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int taille() {
		return buffSize;
	}

}
