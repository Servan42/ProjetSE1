package jus.poc.prodcons.step4;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.Message;

public class Consommateur extends Acteur implements _Consommateur {
	private int nbMsgLus = 0;
	private ProdCons tampon;
	private Message messageRetire;
	private double productionDelay;
	
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
				messageRetire = tampon.get(this);
				observateur.retraitMessage(this, messageRetire);
				System.out.println("Consommateur " + identification() + " lis {" + messageRetire.toString()+"}");
			} catch(Exception e) {
				System.out.println("Consommateur " + identification() + " " + e.toString());
			}
			
			try {
				productionDelay = moyenneTempsDeTraitement - deviationTempsDeTraitement + Math.random()*(2*deviationTempsDeTraitement+1);
				sleep((long) productionDelay);
				observateur.consommationMessage(this, messageRetire, (int) productionDelay);
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
