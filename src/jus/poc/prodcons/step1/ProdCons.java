package jus.poc.prodcons.step1;

import java.util.ArrayList;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

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
			
		}
	}

	@Override
	public int taille() {
		return buffSize;
	}

}
