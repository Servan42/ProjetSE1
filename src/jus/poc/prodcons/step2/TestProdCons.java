package jus.poc.prodcons.step2;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

/**
 * Classe priciplale du programme, qui lis le fichier XML et lance la
 * simulation.
 * 
 * @author CHANET CHARLOT
 *
 */
public class TestProdCons extends Simulateur {

	int nbProd;
	int nbCons;
	int nbBuffer;
	int tempsMoyenProduction;
	int deviationTempsMoyenProduction;
	int tempsMoyenConsommation;
	int deviationTempsMoyenConsommation;
	int nombreMoyenDeProduction;
	int deviationNombreMoyenDeProduction;
	int nombreMoyenNbExemplaire;
	int deviationNombreMoyenNbExemplaire;

	ProdCons tampon;
	int nbMsg;
	Producteur Prod[];
	Consommateur Cons[];
	int nbConsommes = 0;

	/**
	 * Constructeur de TestProdCons
	 * 
	 * @param observateur
	 *            Observateur du programme
	 */
	public TestProdCons(Observateur observateur) {
		super(observateur);
	}

	/**
	 * Corps du programme principal. Initialise le fichier XML et execute les
	 * Producteurs et Consommateurs, puis test la condition de fin.
	 */
	protected void run() throws Exception {
		String filename = "option.xml";
		init("jus/poc/prodcons/option/" + filename);
		tampon = new ProdCons(nbBuffer);
		Prod = new Producteur[nbProd];
		Cons = new Consommateur[nbCons];

		for (int i = 0; i < Math.max(nbProd, nbCons); i++) {
			if (i < nbProd) {
				Prod[i] = new Producteur(tampon, observateur, tempsMoyenProduction, deviationTempsMoyenProduction,
						nombreMoyenDeProduction, deviationNombreMoyenDeProduction);
				nbMsg += Prod[i].nombreDeMessages();
				Prod[i].start();
			}
			if (i < nbCons) {
				Cons[i] = new Consommateur(tampon, observateur, tempsMoyenConsommation,
						deviationTempsMoyenConsommation);
				Cons[i].start();
			}
		}

		// printAtr();
		end();
	}

	/**
	 * Retreave the parameters of the application.
	 * 
	 * @param file
	 *            the final name of the file containing the options.
	 */
	protected void init(String file) {
		Properties properties = new Properties();
		try {
			properties.loadFromXML(ClassLoader.getSystemResourceAsStream(file));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String key;
		int value;
		Class<?> thisOne = getClass();
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			key = (String) entry.getKey();
			value = Integer.parseInt((String) entry.getValue());
			try {
				thisOne.getDeclaredField(key).set(this, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Affiche la valeur de tous les attirbuts de la classe.
	 */
	private void printAtr() {
		System.out.println("nbProd :" + nbProd);
		System.out.println("nbCons :" + nbCons);
		System.out.println("nbBuffer :" + nbBuffer);
		System.out.println("tempsMoyenProduction :" + tempsMoyenProduction);
		System.out.println("deviationTempsMoyenProduction :" + deviationTempsMoyenProduction);
		System.out.println("tempsMoyenConsommation :" + tempsMoyenConsommation);
		System.out.println("deviationTempsMoyenConsommation :" + deviationTempsMoyenConsommation);
		System.out.println("nombreMoyenDeProduction :" + nombreMoyenDeProduction);
		System.out.println("deviationNombreMoyenDeProduction :" + deviationNombreMoyenDeProduction);
		System.out.println("nombreMoyenNbExemplaire :" + nombreMoyenNbExemplaire);
		System.out.println("deviationNombreMoyenNbExemplaire :" + deviationNombreMoyenNbExemplaire);
	}

	/**
	 * Méthode qui permet de maintenir le thread en vie tant que la condition de
	 * fin n'est pas atteinte.
	 */
	private synchronized void end() {
		int nbC = 0;
		while (nbC < nbMsg) {
			nbC = 0;

			// try {
			// System.out.println(" nbC " + nbC + ", nbM " + nbMsg);
			// wait();
			//
			// } catch (InterruptedException e) {
			// System.out.println(e.toString());
			// }

			for (int i = 0; i < Cons.length; i++)
				nbC += Cons[i].nombreDeMessages();

		}

	}

	/**
	 * Fonction principale
	 * 
	 * @param args
	 *            Arguments du programme
	 */
	public static void main(String[] args) {
		new TestProdCons(new Observateur()).start();
	}
}