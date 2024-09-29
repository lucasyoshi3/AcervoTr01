package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fatec.Acervo.model.Aluno;

import repository.AlunoRepository;

@Controller
public class AlunoController {
	
	@Autowired
	private AlunoRepository alunoRepository;

	
	@GetMapping("/aluno")
	public ModelAndView aluno() {
		ModelAndView mv = new ModelAndView("Aluno_form.html");
		return mv;
	}
	
	
	public ModelAndView alunosCreate(@ModelAttribute("aluno")Aluno aluno, @RequestParam("acao") String acao) {
		
		if(acao.equals("gravar")) {
			alunoRepository.inserirAluno(aluno.getCpf(), aluno.getNome(), aluno.getSenha());
		}else if(acao.equals("atualizar")) {
			alunoRepository.atualizarAluno(aluno.getCpf(), aluno.getSenha());
		}
		
		ModelAndView mv = new ModelAndView("Aluno_form");
		
		return mv;
	}
}
