package it.uniroma3.siw.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Opera;


@Component
public class OperaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Opera.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Opera opera = (Opera) target;

        

        // Validazione del campo "collocazione" (Area)
        if (opera.getCollocazione() == null) {
            errors.rejectValue("collocazione", "opera.collocazione.required", "La collocazione dell'opera è obbligatoria.");
        }

        // Validazione del campo "artista"
        if (opera.getArtista() == null) {
            errors.rejectValue("artista", "opera.artista.required", "L'artista dell'opera è obbligatorio.");
        }
    }
}