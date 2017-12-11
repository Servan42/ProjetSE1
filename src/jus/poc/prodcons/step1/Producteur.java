package jus.poc.prodcons.step1;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {

	private int NbMessages;
	private MessageX[] Messages;

	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		NbMessages = (int) Math.random() * 10 + 11;
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
			Messages[i] = new MessageX(super.identification(), i);
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
		// TODO Auto-generated method stub

	}

}
