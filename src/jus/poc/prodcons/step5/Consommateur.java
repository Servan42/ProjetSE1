package jus.poc.prodcons.step5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

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
	private Lock lock;
	private Condition notEnd;
	
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
	 * @param lock
	 *            le Lock utilise pour l'attente passive de TestProdCons
	 * @param notEnd
	 *            la Condition permettant de reveiller le thread de TestProdCons
	 *            apres consommation d'un message
	 * @throws ControlException
	 */
	protected Consommateur(ProdCons tampon, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, Lock lock, Condition notEnd) throws ControlException {
		super(Acteur.typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.tampon = tampon;
		this.setDaemon(true);
		this.lock = lock;
		this.notEnd = notEnd;
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
	 * Methode qui permet au Consommateur de recuperer le message dans le
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
			lock.lock();
			try {
				notEnd.signal();
			} finally {
				lock.unlock();
			}
		}
	}
}
