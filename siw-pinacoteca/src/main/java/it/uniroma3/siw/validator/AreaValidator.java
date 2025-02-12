package it.uniroma3.siw.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Area;
import it.uniroma3.siw.repository.AreaRepository;

@Component
public class AreaValidator implements Validator {

    private final AreaRepository areaRepository;

    public AreaValidator(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Area.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Area area = (Area) target;

        // Validazione del campo "nome"
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "area.nome.required", "Il nome dell'area è obbligatorio.");
        if (area.getNome() != null && (area.getNome().length() < 2 || area.getNome().length() > 100)) {
            errors.rejectValue("nome", "area.nome.length", "Il nome dell'area deve essere compreso tra 2 e 100 caratteri.");
        }
        if (area.getNome() != null && areaRepository.existsByNome(area.getNome())) {
            errors.rejectValue("nome", "area.nome.unique", "Esiste già un'area con questo nome.");
        }

        // Validazione del campo "curatore" (opzionale)
        // Se il curatore è presente, assicurarsi che sia valido
        if (area.getCuratore() != null && area.getCuratore().getArea() != area) {
            errors.rejectValue("curatore", "area.curatore.invalid", "Il curatore associato non è valido.");
        }
    }
}