package jus.poc.prodcons.step3;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur {
	private int nbMsgLus = 0;
	private ProdCons tampon;
	
	protected Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		System.out.println("L'ancien constructeur de consommateur a ete utilise");
		this.setDaemon(true);
	}
	
	protected Consommateur(ProdCons tampon, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.tampon = tampon;
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
				System.out.println("Consommateur " + identification() + " lis {" + tampon.get(this).toString()+"}");
			} catch(Exception e) {
				System.out.println("Consommateur " + identification() + " " + e.toString());
			}
			
			try {
				sleep((long)(moyenneTempsDeTraitement - deviationTempsDeTraitement + 1 + Math.random()*2*deviationTempsDeTraitement));
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			System.out.println("Consommateur " + identification() + " fin consommation");
			nbMsgLus++;
//			synchronized(this) {
//				System.out.println("POOOOOOOOOOOOOOOOOO");
//				notifyAll();
//			}
		}
	}
}
