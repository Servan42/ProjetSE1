package jus.poc.prodcons.step6;

import java.util.Date;
import java.util.HashMap;

import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

/**
 * 
 * @authors CHANET CHARLOT
 *
 *          L'Observer permet d'analyser l'ensemble des evenements pertinents
 *          dans le systeme Producteur/consommateur. Ces evenements sont :
 *          depotMessage retraitMessage productionMessage consommationMessage
 *          newProducteur newConsommateur
 */
public class Observer {

	private boolean coherent;
	private int nbProd;
	private int nbCons;
	private long lastRetrait;
	private HashMap<_Consommateur, Long> retraits;
	private HashMap<_Producteur, Integer> prods;
	private HashMap<_Producteur, Long> depots;

	/**
	 * Constructeur
	 */
	public Observer() {
		lastRetrait = 0;
		prods = new HashMap<_Producteur, Integer>();
		retraits = new HashMap<_Consommateur, Long>();
		depots = new HashMap<_Producteur, Long>();
	}

	/**
	 * initialisation de l'observateur
	 * 
	 * @param nbproducteurs
	 *            le nombre de producteurs devant etre crees
	 * @param nbconsommateurs
	 *            le nombre de constructeurs devant etre crees
	 * @param nbBuffers
	 *            la taille du buffer qui sera utilise
	 * @throws Exception
	 */
	void init(int nbproducteurs, int nbconsommateurs, int nbBuffers) throws Exception {
		coherent = nbproducteurs > 0 && nbconsommateurs > 0 && nbBuffers > 0;
		if (!coherent)
			throw new Exception("Initialisation incorrecte");

		nbProd = nbproducteurs;
		nbCons = nbconsommateurs;
	}

	/**
	 * Evenement correspondant a la creation d'un nouveau consommateur
	 * 
	 * @param c
	 *            le consommateur cree
	 * @throws Exception
	 */
	void newConsommateur(_Consommateur c) throws Exception {
		if (retraits.containsKey(c))
			throw new Exception("Consommateur cree en double - " + c.identification());
		else
			retraits.put(c, new Long(0));

		nbCons--;
		if (nbCons < 0)
			throw new Exception("Trop de consommateurs crees");
	}

	/**
	 * Evenement correspondant a la creation d'un nouveau producteur
	 * 
	 * @param p
	 *            le producteur cree
	 * @throws Exception
	 */
	void newProducteur(_Producteur p) throws Exception {
		if (prods.containsKey(p))
			throw new Exception("Producteur cree en double - " + p.identification());
		else {
			prods.put(p, 0);
			depots.put(p, new Long(0));
		}

		nbProd--;
		if (nbProd < 0)
			throw new Exception("Trop de producteurs crees");
	}

	/**
	 * Evenement correspondant a la consommation d'un message
	 * 
	 * @param c
	 *            le consommateur concerne
	 * @param m
	 *            le message concerne
	 * @param tempsDeTraitement
	 *            le temps de traitement necessaire a la consommation de m par c
	 * @throws Exception
	 */
	void consommationMessage(_Consommateur c, MessageX m, int tempsDeTraitement) throws Exception {
		long t = new Date().getTime();
		if (!retraits.containsKey(c))
			throw new Exception("Consommateur mal cree - " + c.identification());

		if ((t - retraits.get(c)) < tempsDeTraitement)
			throw new Exception("Temps de consommation non respecte - Consommateur " + c.identification());
	}

	/**
	 * Evenement correspondant au depot d'un message dans le tampon
	 * 
	 * @param p
	 *            le producteur concerne
	 * @param m
	 *            le message concerne
	 * @throws Exception
	 */
	void depotMessage(_Producteur p, MessageX m) throws Exception {
		if (!prods.containsKey(p))
			throw new Exception("Producteur mal cree - " + p.identification());

		if (m.getId() != p.identification())
			throw new Exception("Producteur " + p.identification() + " put message id producteur " + m.getId());

		if (m.getNb() != prods.get(p) + 1)
			throw new Exception("Put mauvais ordre producteur " + p.identification());
		else {
			depots.put(p, m.getTime());
			prods.put(p, m.getNb());
		}
	}

	/**
	 * Evenement correspondant a la production d'un message
	 * 
	 * @param p
	 *            le producteur concerne
	 * @param m
	 *            le message concerne
	 * @param tempsDeTraitement
	 *            le temps de traitement necessaire a la production de m par p
	 * @throws Exception
	 */
	void productionMessage(_Producteur p, MessageX m, int tempsDeTraitement) throws Exception {
		long t = new Date().getTime();
		if (!prods.containsKey(p))
			throw new Exception("Producteur mal cree - " + p.identification());

		if (m.getId() != p.identification())
			throw new Exception("Message mal produit producteur - " + p.identification());

		if ((t - depots.get(p)) < tempsDeTraitement)
			throw new Exception("Temps de production non respecte - Producteur " + p.identification());
	}

	/**
	 * Evenement correspondant au retrait d'un message du tampon
	 * 
	 * @param c
	 *            le consommateur concerne
	 * @param m
	 *            le message concerne
	 * @throws Exception
	 */
	void retraitMessage(_Consommateur c, MessageX m) throws Exception {
		if (!retraits.containsKey(c))
			throw new Exception("Consommateur mal cree - " + c.identification());

		if (m.getTime() < lastRetrait)
			throw new Exception("Mauvais ordre de retrait");
		else
			lastRetrait = m.getTime();
	}

	/**
	 * indicateur de coherence du controle le controle est coherent s'il a ete
	 * correctement initialise
	 * 
	 * @return
	 */
	boolean coherent() {
		return coherent;
	}
}
