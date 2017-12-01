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

	public TestProdCons(Observateur observateur) {
		super(observateur);
	}

	/**
	 * Corps du programme principal
	 */
	protected void run() throws Exception {
		String filename = "option.xml";
		init("jus/poc/prodcons/option/" + filename);
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

	public static void main(String[] args) {
		new TestProdCons(new Observateur()).start();
		System.out.println("End");
	}
}