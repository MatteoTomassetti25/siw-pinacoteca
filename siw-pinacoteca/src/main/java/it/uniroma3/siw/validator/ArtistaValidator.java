package it.uniroma3.siw.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Artista;


@Component
public class ArtistaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Artista.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Artista artista = (Artista) target;

        
        // Validazione del campo "dataNascita"
        if (artista.getDataNascita() == null) {
            errors.rejectValue("dataNascita", "artista.dataNascita.required", "La data di nascita è obbligatoria.");
        }

        // Validazione del campo "luogoNascita"
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "luogoNascita", "artista.luogoNascita.required", "Il luogo di nascita è obbligatorio.");

        // Verifica la coerenza tra dataNascita e dataMorte
        if (artista.getDataNascita() != null && artista.getDataMorte() != null) {
            if (artista.getDataMorte().isBefore(artista.getDataNascita())) {
                errors.rejectValue("dataMorte", "artista.dataMorte.invalid", "La data di morte non può essere precedente alla data di nascita.");
            }
        }
    }
}