package com.fatec.Acervo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.Acervo.model.Exemplar;
import com.fatec.Acervo.repository.ExemplarRepository;

@Controller
public class ExemplarController {
	
	@Autowired
	private ExemplarRepository exemplarRepository;
	
	private List<Exemplar> lista = new ArrayList<>();
	
	@GetMapping("/exemplar")
	private ModelAndView exemplar() {
		ModelAndView mv = new ModelAndView("Exemplar_form");
		mv.addObject("exemplar", new Exemplar());
		
		return mv;
	}
	
	@PostMapping("/exemplar")
	private ModelAndView exemplarCriar(@ModelAttribute("exemplar")Exemplar exemplar, @RequestParam("acao") String acao) {
		ModelAndView mv = new ModelAndView("Exemplar_form");
		String resposta = null;
		if(acao.equals("gravar")) {
			resposta = exemplarRepository.inserirExemplar(exemplar.getCodigo(), exemplar.getNome(), exemplar.getQtdPaginas());
		}
		
		if(acao.equals("atualizarNome")) {
			exemplarRepository.atualizarNomeExemplar(exemplar.getCodigo(), exemplar.getNome());
		}
		
		if(acao.equals("atualizarQtdPagina")) {
			exemplarRepository.atualizarQtdPaginasExemplar(exemplar.getCodigo(), exemplar.getQtdPaginas());
		}
		
		mv.addObject("exemplar",new Exemplar());
		mv.addObject("resposta",resposta);
		
		return mv;
	}
	
	@GetMapping("/verificarExemplares")
	private ModelAndView verificarExemplar() {
		ModelAndView mv = new ModelAndView("VerificarExemplar_form");
		mv.addObject("exemplar", new Exemplar());
		
		return mv;
	}
	
	@PostMapping("/verificarExemplares")
	private ModelAndView verificarExemplarCreate(@ModelAttribute("exemplar")Exemplar exemplar, @RequestParam("acao") String acao) {
		ModelAndView mv = new ModelAndView("VerificarExemplar_form");
		
		if(acao.equals("pesquisar")) {
			lista =  exemplarRepository.findByCodigo(exemplar.getCodigo());
		}
		mv.addObject("lista",lista);
		mv.addObject("exemplar",new Exemplar());
		
		return mv;
	}
}
