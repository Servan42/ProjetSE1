package jus.poc.prodcons.step1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur {
	int nbMsgLus = 0;
	
	protected Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.setDaemon(true);
	}

	@Override
	public int nombreDeMessages() {
		return nbMsgLus;
	}

	@Override
	public void run() {
		while(true) {
			try {
				TestProdCons.tampon.get(this);
			} catch(Exception e) {
				System.out.println(e.toString());
			}
		}
	}
}
