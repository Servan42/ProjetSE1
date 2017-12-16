package jus.poc.prodcons.step4;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.Message;

/**
 * Classe permettant la gestion des consommateurs.
 * 
 * @author CHANET CHARLOT
 *
 */
public class Consommateur extends Acteur implements _Consommateur {
	private int nbMsgLus = 0;
	private ProdCons tampon;
	private Message messageRetire;
	private double productionDelay;

	/**
	 * Constructeur de Consommateur
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
	protected Consommateur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		System.out.println("L'ancien constructeur de consommateur a ete utilise");
		this.setDaemon(true);
	}

	/**
	 * Constructeur de Consommateur
	 * 
	 * @param tampon
	 *            Buffer dans lequel le consommateur devra recuperer le message.
	 * @param observateur
	 *            Observateur du programme
	 * @param moyenneTempsDeTraitement
	 *            Valeur du champ du même nom dans le fichier XML de test.
	 * @param deviationTempsDeTraitement
	 *            Valeur du champ du même nom dans le fichier XML de test.
	 * @throws ControlException
	 */
	protected Consommateur(ProdCons tampon, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.tampon = tampon;
		this.setDaemon(true);
	}

	/**
	 * Getter
	 * 
	 * @return Le nombre de messages lus par le consommateur
	 */
	public int nombreDeMessages() {
		return nbMsgLus;
	}

	/**
	 * Methode qui permet au Consommateur de reccuperer le message dans le
	 * tampon, et d'attendre son temps de production.
	 */
	public void run() {
		while (true) {
			try {
				messageRetire = tampon.get(this);
				observateur.retraitMessage(this, messageRetire);
			} catch (Exception e) {
				System.out.println("Consommateur " + identification() + " " + e.toString());
			}

			try {
				productionDelay = moyenneTempsDeTraitement - deviationTempsDeTraitement
						+ Math.random() * (2 * deviationTempsDeTraitement + 1);
				sleep((long) productionDelay);
				observateur.consommationMessage(this, messageRetire, (int) productionDelay);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			System.out.println("Consommateur " + identification() + " fin consommation");
			nbMsgLus++;
			// synchronized(this) {
			// notifyAll();
			// }
		}
	}
}
