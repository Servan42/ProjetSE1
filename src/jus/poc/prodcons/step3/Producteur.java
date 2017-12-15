package jus.poc.prodcons.step3;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {

	private int NbMessages;
	private MessageX[] Messages;
	private ProdCons tampon;
	double productionDelay;

	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		NbMessages = (int) Math.random() * 10 + 11;
		Messages = new MessageX[NbMessages];
		initMessages(Messages);
		System.out.println("L'ancien constructeur de Producteur à été utilisé");
	}
	
	protected Producteur(ProdCons tampon, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, int nbMoyenProduction, int deviationNbMoyenProduction) throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.tampon = tampon;
		NbMessages = (int)(nbMoyenProduction - deviationNbMoyenProduction +1 +Math.random()*2*deviationNbMoyenProduction);
		Messages = new MessageX[NbMessages];
		initMessages(Messages);
	}

	/**
	 * Initialise les messages qui seront envoyés par le producteur.
	 * 
	 * @param Messages
	 *            Le tableau de chaines de caractères qui contiendra les messages à
	 *            l'issu de la méthode.
	 */
	private void initMessages(MessageX[] Messages) {
		for (int i = 0; i < NbMessages; i++) {
			Messages[i] = new MessageX(identification(), i+1);
		}
	}

	@Override
	/**
	 * Renvoie le nombre de messages à traiter.
	 * 
	 * @return Nombre de messages que le producteur doit produire (invariable)
	 */
	public int nombreDeMessages() {
		return NbMessages;
	}

	@Override
	public void run() {
		for (int i = 0; i < NbMessages; i++) {
			
			try {
				System.out.println("Producteur " + identification() + " production message " + (i+1));
				productionDelay = moyenneTempsDeTraitement - deviationTempsDeTraitement + 1 + Math.random()*2*deviationTempsDeTraitement;
				sleep((long) productionDelay);

			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
			try {
				observateur.productionMessage(this, Messages[i], (int) productionDelay);
			} catch (ControlException e) {
				System.out.println(e.toString());
			}
			
			try {
				tampon.put(this, Messages[i]);
				observateur.depotMessage(this, Messages[i]);
			} catch (InterruptedException e) {
				System.out.println(e.toString());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

}
