package br.com.artesao.Validador;

import br.com.artesao.objetos.Artesao;

public class Validador {
	
	private Artesao cliente;
	
	boolean protecao = true;
	
	public Validador(Artesao cliente){
		
		this.cliente = cliente;
	}
	
	public String valida(String recebe){
		
		String nome = cliente.getNome();
		String telefone = cliente.getTelefone();
		
		String reg1x = "[A-Za-z]+$";
		String reg2x = "\\(?[0-9]{2}\\)? ?[0-9]{4}-?[0-9]{4}";
		
		if(!nome.matches(reg1x)){
			recebe = "O campo nome n�o pode conter n�meros.<br>";
			protecao = false;
		}
		
		if(!telefone.matches(reg2x)){
			recebe += "O campo telefone n�o pode conter letras.<br>";
			protecao = false;
		}
		
		if(cliente.getNome() == ""){
			recebe += "O campo nome � obrigat�rio!<br>";
			protecao = false;
		}
		
		if(cliente.getEndereco() == ""){
			recebe += "O campo endere�o � obrigat�rio!<br>";
			protecao = false;
		}
		
		if(cliente.getTelefone() == ""){
			recebe += "O campo telefone � obrigat�rio!<br>";
			protecao = false;
		}
		
		if(protecao == false){
			
			return recebe;
			
		} else {
			
			return recebe;
			
		}
	}
}
