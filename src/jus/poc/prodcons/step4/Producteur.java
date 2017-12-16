package jus.poc.prodcons.step4;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {

	private int NbMessages;
	private MessageX[] Messages;
	private ProdCons tampon;
	private double productionDelay;
	private int nombreMoyenNbExemplaire;
	private int deviationNombreMoyenNbExemplaire;

	protected Producteur(int type, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement) throws ControlException {
		super(type, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		NbMessages = (int) Math.random() * 10 + 11;
		Messages = new MessageX[NbMessages];
		initMessages(Messages);
		System.out.println("L'ancien constructeur de Producteur à été utilisé");
	}
	
	protected Producteur(ProdCons tampon, Observateur observateur, int moyenneTempsDeTraitement,
			int deviationTempsDeTraitement, int nbMoyenProduction, int deviationNbMoyenProduction, int nombreMoyenNbExemplaire, int deviationNombreMoyenNbExemplaire) throws ControlException {
		super(Acteur.typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		this.tampon = tampon;
		NbMessages = (int)(nbMoyenProduction - deviationNbMoyenProduction +Math.random()*(2*deviationNbMoyenProduction+1));
		Messages = new MessageX[NbMessages];
		this.nombreMoyenNbExemplaire = nombreMoyenNbExemplaire;
		this.deviationNombreMoyenNbExemplaire = deviationNombreMoyenNbExemplaire;
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
		int nbExemplaires;
		for (int i = 0; i < NbMessages; i++) {
			nbExemplaires = (int) (nombreMoyenNbExemplaire - deviationNombreMoyenNbExemplaire + Math.random()*(2*deviationNombreMoyenNbExemplaire+1));
			Messages[i] = new MessageX(identification(), i+1,nbExemplaires);
		}
	}

	@Override
	/**
	 * Renvoie le nombre de messages à traiter.
	 * 
	 * @return Nombre de messages que le producteur doit produire (invariable)
	 */
	public int nombreDeMessages() {
		int total = 0;
		for(int i = 0; i<NbMessages; i++)
			total += Messages[i].Exemplaires();
		
		return total;
	}

	@Override
	public void run() {
		for (int i = 0; i < NbMessages; i++) {
			
			try {
				productionDelay = moyenneTempsDeTraitement - deviationTempsDeTraitement + Math.random()*(2*deviationTempsDeTraitement+1);
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
