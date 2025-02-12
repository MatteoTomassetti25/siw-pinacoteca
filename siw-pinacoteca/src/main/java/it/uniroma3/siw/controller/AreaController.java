package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Area;
import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.service.AreaService;
import it.uniroma3.siw.service.CuratoreService;
import it.uniroma3.siw.validator.AreaValidator;
import jakarta.validation.Valid;

@Controller
public class AreaController {

    @Autowired
    protected AreaService areaService;

    @Autowired
    protected CuratoreService curatoreService;
    
    @Autowired
    protected AreaValidator areaValidator;

    @GetMapping("/admin/operazioniAree")
    public String operazioniAree() {
        return "admin/operazioniAree";
    }

    @GetMapping("/admin/inserimentoArea")
    public String inserimentoArea(Model model) {
        model.addAttribute("area", new Area());
        model.addAttribute("curatori", this.curatoreService.findAll());
        return "admin/formNewArea";
    }

    @PostMapping("/admin/salvaArea")
    public String salvaArea(@RequestParam("nome") String nome, 
                            @RequestParam(value = "curatore", required = false) Long curatoreId, 
                            Model model,@Valid @ModelAttribute("area") Area area, BindingResult bindingResult) {
        
        Area nuovaArea = new Area();
        nuovaArea.setNome(nome);

        if (curatoreId != null) {
            Curatore curatore = curatoreService.findById(curatoreId);
            nuovaArea.setCuratore(curatore);
        } else {
            nuovaArea.setCuratore(null);
        }
        
        this.areaValidator.validate(nuovaArea, bindingResult);
        
        if(bindingResult.hasErrors()) {
        	model.addAttribute("messaggioErrore", "Errore nella validazione del form");
        	return "admin/formNewArea";
        }

        areaService.save(nuovaArea);
        model.addAttribute("messaggioConferma", "Area creata con successo!");

        
        return "admin/confermaSalvataggio";
    }


    @GetMapping("/admin/listaAree")
    public String listaAree(Model model) {
        model.addAttribute("aree", this.areaService.findAll());
        return "admin/listaAree";
    }

    @GetMapping("/admin/modificaArea/{id}")
    public String modificaArea(Model model, @PathVariable("id") Long id) {
        Area area = this.areaService.findById(id);
        model.addAttribute("area", area);
        model.addAttribute("curatori", this.curatoreService.findAll());
        return "admin/formModificaArea";
    }

    @PostMapping("/admin/salvaModifiche/{id}")
    public String salvaModificheArea(@PathVariable("id") Long id, 
                                     @RequestParam("nome") String nome, 
                                     @RequestParam(value = "curatore", required = false) Long curatoreId, 
                                     Model model, @Valid @ModelAttribute("area") Area area, BindingResult bindingResult) {
        
        Area areaEsistente = this.areaService.findById(id);
        areaEsistente.setNome(nome);

        if (curatoreId != null) {
            Curatore curatore = this.curatoreService.findById(curatoreId);
            areaEsistente.setCuratore(curatore);
        } else {
            areaEsistente.setCuratore(null);
        }
        
     // Verifica se ci sono errori di validazione
	     if (bindingResult.hasErrors()) {
	         List<String> errorMessages = bindingResult.getFieldErrors()
	             .stream()
	             .map(error -> error.getField() + ": " + error.getDefaultMessage())
	             .toList();
	         model.addAttribute("errorMessages", errorMessages); // Aggiungi messaggi di errore dettagliati
	         return "admin/formModificaArea";
	     }


        this.areaService.save(areaEsistente);
        model.addAttribute("messaggioConferma", "Modifiche salvate con successo!");

        return "admin/confermaSalvataggio";
    }


    @GetMapping("/admin/eliminaArea/{id}")
    public String eliminaArea(@PathVariable("id") Long id, Model model) {
 
            Area area = this.areaService.findById(id);

            if (area == null) {
                model.addAttribute("messaggioErrore", "Errore: area non trovata.");
                return "admin/listaAree";
            }

            // Dissocia il curatore associato, se presente
            Curatore curatore = area.getCuratore();
            if (curatore != null) {
//                curatore.setArea(null); // Dissocia il curatore dall'area
//                this.curatoreService.save(curatore); // Salva le modifiche al curatore
                model.addAttribute("messaggioErrore", "Attenzione l'area appartiene ad un curatore!");
                model.addAttribute("aree", this.areaService.findAll());
                return "admin/listaAree";
            }
            
            if(!this.areaService.findAllOpere(id).isEmpty()) {
            	 model.addAttribute("messaggioErrore", "L'area contiene delle opere");
            	 model.addAttribute("aree", this.areaService.findAll());
                 return "admin/listaAree";
            }

            // Elimina l'area
            this.areaService.deleteById(id);
            model.addAttribute("messaggioConferma", "Area eliminata con successo.");
        

        // Ritorna alla lista delle aree aggiornata
        model.addAttribute("aree", this.areaService.findAll());
        return "admin/listaAree";
    }
}
