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

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.repository.CredenzialiRepository;
import it.uniroma3.siw.repository.OperaRepository;
import it.uniroma3.siw.service.AreaService;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.OperaService;
import it.uniroma3.siw.validator.OperaValidator;
import jakarta.validation.Valid;



@Controller
public class OperaController {

	@Autowired
	protected OperaRepository operaRepository;

	@Autowired
	protected OperaService operaService;

	@Autowired
	protected CredenzialiRepository credenzialiRepository;

	@Autowired
	protected ArtistaService artistaService;

	@Autowired
	protected AreaService areaService;

	@Autowired
	protected OperaValidator operaValidator;


	@GetMapping("/admin/operazioniOpere")
	public String operazioniOpere() {
		return "admin/operazioniOpere";
	}


	@GetMapping("/admin/inserimentoOpera")
	public String inserimentoOpera(Model model) {
		model.addAttribute("opera", new Opera());
		model.addAttribute("artisti", this.artistaService.findAll());
		model.addAttribute("aree", this.areaService.findAll());
		return "admin/formNewOpera";
	}


	@PostMapping("/admin/salvaOpera")
	public String salvaOpera(@Valid @ModelAttribute("opera") Opera opera,BindingResult bindingResult , Model model) {

		this.operaValidator.validate(opera, bindingResult);

		if(bindingResult.hasErrors()) {
			model.addAttribute("artisti", this.artistaService.findAll());
			model.addAttribute("aree", this.areaService.findAll());
			return "admin/formNewOpera";
		}

		this.operaService.save(opera);
		model.addAttribute("messaggio", "Opera salvata con successo");

		return "admin/confermaSalvataggio";
	}


	@GetMapping("/admin/listaOpere")
	public String listaOpere(Model model) {

		model.addAttribute("opere", this.operaService.findAll());
		return "admin/listaOpere";
	}

	@GetMapping("/admin/modificaOpera/{id}")
	public String modificaOpera(@PathVariable("id") Long id, Model model) {

		Opera opera = this.operaService.findById(id);
		model.addAttribute("opera", opera);
		model.addAttribute("artisti", this.artistaService.findAll());
		model.addAttribute("collocazioni", this.areaService.findAll());

		return "admin/formModificaOpera";
	}

	@PostMapping("/admin/salvaModificheOpera/{id}")
	public String salvaModificheOpera(@PathVariable("id") Long id,@Valid @ModelAttribute("opera") Opera opera,BindingResult bindingResult, Model model ) {


		Opera operaEsistente = this.operaService.findById(id);

		operaEsistente.setTitolo(opera.getTitolo());
		operaEsistente.setTecnica(opera.getTecnica());
		operaEsistente.setAnno(opera.getAnno());
		operaEsistente.setArtista(opera.getArtista());
		operaEsistente.setCollocazione(opera.getCollocazione());
		
		this.operaValidator.validate(opera, bindingResult);

		if(bindingResult.hasErrors()) {
			model.addAttribute("artisti", this.artistaService.findAll());
			model.addAttribute("collocazioni", this.areaService.findAll());
			return "admin/formModificaOpera";
		}

		this.operaService.save(operaEsistente);
		model.addAttribute("messaggio", "opera modificata con successo");

		return"admin/confermaSalvataggio";
	}


	@GetMapping("/admin/eliminaOpera/{id}")
	public String eliminaOpera(@PathVariable("id") Long id, Model model) {

		this.operaService.deleteById(id);
		model.addAttribute("messaggio", "eliminazione successo");

		return "admin/confermaSalvataggio";
	}

	@GetMapping("/listaOpere")
	public String listaOpereUtente(Model model) {

		model.addAttribute("opere", this.operaService.findAll());
		return "listaOpereUtente";
	}

	@GetMapping("/visualizzaDettaglio/{id}")
	public String visualizza(@PathVariable("id") Long id, Model model) {

		Opera opera = this.operaService.findById(id);

		model.addAttribute("opera", opera);
		return "visualizzaOpera";
	}


	@GetMapping("/filtraOpere")
	public String filtraOpere(Model model) {
		model.addAttribute("artisti", artistaService.findAll());
		model.addAttribute("anni", operaService.findAllAnni());
		model.addAttribute("tecniche", operaService.findAllTecniche());
		return "listaOpereFiltrate";
	}

	@GetMapping("/risultatiFiltri")
	public String risultatiFiltri(@RequestParam(required = false) Long artistaId,
			@RequestParam(required = false) Integer anno,
			@RequestParam(required = false) String tecnica,
			Model model) {

		System.out.println("Filtri selezionati - Artista: " + artistaId + ", Anno: " + anno + ", Tecnica: " + tecnica);

		// Normalizza i valori nulli o vuoti
		if (tecnica != null && tecnica.trim().isEmpty()) {
			tecnica = null;
		}

		List<Opera> opereFiltrate = operaService.findbyFiltri(artistaId, anno, tecnica);

		System.out.println("Opere trovate: " + opereFiltrate.size());

		// Recupera l'artista solo se è stato selezionato
		Artista artista = (artistaId != null) ? artistaService.findById(artistaId) : null;

		// Passa i dati alla view
		model.addAttribute("opere", opereFiltrate);
		model.addAttribute("selectedArtista", artista);
		model.addAttribute("selectedAnno", anno);
		model.addAttribute("selectedTecnica", tecnica);

		return "risultatiFiltri";
	}





}
