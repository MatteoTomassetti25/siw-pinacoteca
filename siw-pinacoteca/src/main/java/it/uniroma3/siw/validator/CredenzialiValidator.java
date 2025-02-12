package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.UtenteRepository;

@Component
public class CredenzialiValidator implements Validator {
	
    @Autowired 
    private UtenteRepository utenteRepository;


	@Override
	public boolean supports(Class<?> clazz) {
		return Credenziali.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Credenziali credenziali = (Credenziali) target;

		if(credenziali.getUsername().isEmpty()) {
			errors.reject("NotBlank.credenziali.username");
		}

		if(credenziali.getPassword().isEmpty()) {
			errors.reject("NotBlank.credenziali.password");
		}

		if(!credenziali.getUsername().isEmpty() && this.utenteRepository.existsByEmail(credenziali.getUsername())) {
			errors.reject("duplicate");
		}

	}



}