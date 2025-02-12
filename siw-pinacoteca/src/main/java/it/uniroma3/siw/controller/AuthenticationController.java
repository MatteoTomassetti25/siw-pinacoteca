package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredenzialiService credenzialiService;

    @Autowired
	private UtenteService utenteService;
	
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("utente", new Utente());
		model.addAttribute("credenziali", new Credenziali());
		
		return "formRegisterUser";
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin.html";
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
			
			if (credenziali.getRole().equals(Credenziali.ADMIN_ROLE)) {
				return "admin/indexAdmin.html";
			}
		}
		
        return "index.html";
	}
		
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credenziali credenziali = credenzialiService.getCredenziali(userDetails.getUsername());
    	
    	if (credenziali.getRole().equals(Credenziali.ADMIN_ROLE)) {
            return "admin/indexAdmin.html";
        }
    	
        return "index.html";
    }

	@PostMapping(value = { "/register" })
    public String registerUtente(@Valid @ModelAttribute("utente") Utente utente,BindingResult userBindingResult, @Valid@ModelAttribute("credenziali") Credenziali credenziali,BindingResult credenzialiBindingResult,Model model) {

		// se utente e credential hanno entrambi contenuti validi, memorizza Utente e the Credenziali nel DB
        if(!userBindingResult.hasErrors() && !credenzialiBindingResult.hasErrors()) {
            utenteService.saveUser(utente);
            credenziali.setUtente(utente);
            credenzialiService.saveCredenziali(credenziali);
            
            model.addAttribute("utente", utente);
            return "registrationSuccessful.html";
        }
        return "formRegisterUser.html";
    }
}
