package br.com.artesao.jdbcinterface;

import java.util.List;

import br.com.artesao.objetos.Artesao;

public interface ArtesaoDAO {
	
	public boolean addCliente(Artesao cliente);
	
	public List<Artesao> buscarPorNome(String nome);
	
	public boolean deletarCliente(int id);
	
	public boolean atualizar(Artesao cliente);
	
//	public boolean login(String usuario, String senha);

}
