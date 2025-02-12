package it.uniroma3.siw.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.repository.CuratoreRepository;

@Component
public class CuratoreValidator implements Validator {

    @Autowired
    private CuratoreRepository curatoreRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Curatore.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Curatore curatore = (Curatore) target;

        // Validazione del campo "nome"
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "curatore.nome.required", "Il nome del curatore è obbligatorio.");
        if (curatore.getNome() != null && (curatore.getNome().length() < 2 || curatore.getNome().length() > 50)) {
            errors.rejectValue("nome", "curatore.nome.length", "Il nome del curatore deve avere tra 2 e 50 caratteri.");
        }

        // Validazione del campo "cognome"
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "curatore.cognome.required", "Il cognome del curatore è obbligatorio.");
        if (curatore.getCognome() != null && (curatore.getCognome().length() < 2 || curatore.getCognome().length() > 50)) {
            errors.rejectValue("cognome", "curatore.cognome.length", "Il cognome del curatore deve avere tra 2 e 50 caratteri.");
        }

        // Validazione del campo "dataNascita"
        if (curatore.getDataNascita() == null) {
            errors.rejectValue("dataNascita", "curatore.dataNascita.required", "La data di nascita è obbligatoria.");
        }

        // Validazione del campo "luogoNascita"
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "luogoNascita", "curatore.luogoNascita.required", "Il luogo di nascita è obbligatorio.");

        // Validazione del campo "codFiscale" (unicità)
        if (curatore.getCodFiscale() != null && curatoreRepository.existsByCodFiscale(curatore.getCodFiscale())) {
            errors.rejectValue("codFiscale", "curatore.codFiscale.unique", "Esiste già un curatore con questo codice fiscale.");
        }
    }
}