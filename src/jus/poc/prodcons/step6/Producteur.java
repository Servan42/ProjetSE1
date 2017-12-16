package jus.poc.prodcons.step6;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

/**
 * Classe permettant la gestion des Producteurs.
 * 
 * @author CHANET CHARLOT
 *
 */
public class Producteur extends Acteur implements _Producteur {

	private int NbMessages;
	private MessageX[] Messages;
	private ProdCons tampon;
	double productionDelay;
	Observer observateur;

	/**
	 * Constructeur de Producteur
	 * 
	 * @param type
	 *            Entier indiquant di on utilise un producteur ou un
	 *            consommateur
	 * @param observateur
	 *            Observateur du programme
	 * @param moyenneTempsDeTraitement
	 *            Valeur du champ du même nom dans le fichier XML de test.
	 * @param deviationTempsDeTraitement
	 *            Valeur du champ du même nom dans le fichier XML de test.
	 * @throws ControlException
	 * @deprecated
	 */
	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		NbMessages = (int) Math.random() * 10 + 11;
		Messages = new MessageX[NbMessages];
		initMessages(Messages);
		System.out.println("L'ancien constructeur de Producteur à été utilisé");
	}

	/**
	 * Constructeur de Producteur
	 * 
	 * @param tampon
	 *            Buffer dans lequel le producteur doit poser ses messages.
	 * @param observateur
	 *            Observateur du programme
	 * @param moyenneTempsDeTraitement
	 *            Valeur du champ du même nom dans le fichier XML de test.
	 * @param deviationTempsDeTraitement
	 *            Valeur du champ du même nom dans le fichier XML de test.
	 * @param nbMoyenProduction
	 *            Valeur du champ du même nom dans le fichier XML de test.
	 * @param deviationNbMoyenProduction
	 *            Valeur du champ du même nom dans le fichier XML de test.
	 * @param obs
	 *            Observateur Reel
	 * @throws ControlException
	 */
	protected Producteur(ProdCons tampon, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, int nbMoyenProduction, int deviationNbMoyenProduction, Observer obs)
			throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.tampon = tampon;
		NbMessages = (int) (nbMoyenProduction - deviationNbMoyenProduction
				+ Math.random() * (2 * deviationNbMoyenProduction + 1));
		Messages = new MessageX[NbMessages];
		initMessages(Messages);
		this.observateur = obs;
	}

	/**
	 * Initialise les messages qui seront envoyés par le producteur.
	 * 
	 * @param Messages
	 *            Le tableau de chaines de caractères qui contiendra les
	 *            messages à l'issu de la méthode.
	 */
	private void initMessages(MessageX[] Messages) {
		for (int i = 0; i < NbMessages; i++) {
			Messages[i] = new MessageX(identification(), i + 1);
		}
	}

	/**
	 * Renvoie le nombre de messages à traiter.
	 * 
	 * @return Nombre de messages que le producteur doit produire (invariable)
	 */
	public int nombreDeMessages() {
		return NbMessages;
	}

	/**
	 * Methode qui permet au Producteur d'attendre son temps de production et de
	 * poser le(s) message(s) dans le tampon
	 */
	public void run() {
		for (int i = 0; i < NbMessages; i++) {

			try {
				System.out.println("Producteur " + identification() + " production message " + (i + 1));
				productionDelay = moyenneTempsDeTraitement - deviationTempsDeTraitement
						+ Math.random() * (2 * deviationTempsDeTraitement + 1);
				sleep((long) productionDelay);

			} catch (InterruptedException e) {
				System.out.println(e.toString());
			}
			try {
				observateur.productionMessage(this, Messages[i], (int) productionDelay);
			} catch (Exception e) {
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
