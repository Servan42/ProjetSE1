package jus.poc.prodcons.step1;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

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

	static ProdCons tampon;
	
	public TestProdCons(Observateur observateur) {
		super(observateur);
	}

	/**
	 * Corps du programme principal
	 */
	protected void run() throws Exception {
		String filename = "option.xml";
		init("jus/poc/prodcons/option/" + filename);
		tampon = new ProdCons(nbBuffer);
		printAtr();
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
	private void printAtr(){
		System.out.println("nbProd :" + nbProd);
		System.out.println("nbCons :" + nbCons);
		System.out.println("nbBuffer :" + nbBuffer);
		System.out.println("tempsMoyenProduction :" + tempsMoyenProduction );
		System.out.println("deviationTempsMoyenProduction :" + deviationTempsMoyenProduction);
		System.out.println("tempsMoyenConsommation :" + tempsMoyenConsommation);
		System.out.println("deviationTempsMoyenConsommation :" + deviationTempsMoyenConsommation);
		System.out.println("nombreMoyenDeProduction :" + nombreMoyenDeProduction);
		System.out.println("deviationNombreMoyenDeProduction :" + deviationNombreMoyenDeProduction);
		System.out.println("nombreMoyenNbExemplaire :" + nombreMoyenNbExemplaire);
		System.out.println("deviationNombreMoyenNbExemplaire :" + deviationNombreMoyenNbExemplaire);
	}

	public static void main(String[] args) {
		new TestProdCons(new Observateur()).start();
		System.out.println("End");
	}
}