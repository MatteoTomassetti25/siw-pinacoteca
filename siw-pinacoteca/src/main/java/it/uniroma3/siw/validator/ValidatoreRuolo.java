package it.uniroma3.siw.validator;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;

public class ValidatoreRuolo {

	public static boolean utenteDefault(Utente utente) {
		return Credenziali.DEFAULT_ROLE.equals(utente.getCredenziali().getRole());
	}
	
	public static boolean utenteAdmin(Utente utente) {
		return Credenziali.ADMIN_ROLE.equals(utente.getCredenziali().getRole());
	}
}


