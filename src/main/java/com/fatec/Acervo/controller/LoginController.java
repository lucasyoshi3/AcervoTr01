package com.fatec.Acervo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.Acervo.model.Aluno;
import com.fatec.Acervo.repository.LoginRepository;

@Controller
public class LoginController {

	@Autowired
	private LoginRepository loginRepository;
	
	@GetMapping("/login")
	private ModelAndView login() {
		ModelAndView mv = new ModelAndView("Login_form");
		mv.addObject("aluno", new Aluno());
		
		return mv;
	}
	
	@PostMapping("/login")
	private String loginAcess(@ModelAttribute("aluno") Aluno aluno,@RequestParam("acao") String acao) {
		Boolean loginValido = false;
		if(acao.equals("acessar")) {
			loginValido = loginRepository.login(aluno.getEmail(), aluno.getSenha());
		}
		
		if(loginValido) {
			return "redirect:/verificarExemplares";
		}
		return "redirect:/login";
	}
	
}
