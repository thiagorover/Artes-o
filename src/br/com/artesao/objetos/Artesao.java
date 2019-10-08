package br.com.artesao.objetos;

import java.io.Serializable;

public class Artesao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private String endereco;
	private String telefone;
	private String sexo;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public String getEndereco(){
		return endereco;
	}
	
	public void setEndereco(String endereco){
		this.endereco = endereco;
	}
	
	public String getTelefone(){
		return telefone;
	}
	
	public void setTelefone(String telefone){
		this.telefone = telefone;
	}
	
	public String getSexo(){
		return sexo;
	}
	
	public void setSexo(String sexo){
		this.sexo = sexo;
	}
}