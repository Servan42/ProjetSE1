package jus.poc.prodcons.step1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {

	private int NbMessages;
	private String[] Messages;

	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		NbMessages = 10;
		Messages = new String[NbMessages];
		initMessages(Messages);
	}

	/**
	 * Initialise les messages qui seront envoyés par le producteur.
	 * 
	 * @param Messages
	 *            Le tableau de chaines de caractères qui contiendra les
	 *            messages à l'issu de la méthode.
	 */
	private void initMessages(String[] Messages) {
		for (int i = 0; i < NbMessages; i++) {
			Messages[i] = "Message " + i + " from Producteur";
		}
	}

	@Override
	/**
	 * Renvoie le nombre de messages à traiter. 
	 * @return Nombre de messages que le producteur doit produire (invariable)
	 */
	public int nombreDeMessages() {
		return NbMessages;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
