package br.com.artesao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.artesao.jdbcinterface.ArtesaoDAO;
import br.com.artesao.objetos.Artesao;
import br.com.artesao.objetos.Usuario;

public class JDBCArtesaoDAO implements ArtesaoDAO {

	private Connection conexao;

	public JDBCArtesaoDAO(Connection conexao){
		this.conexao = conexao;
	}

	public boolean addCliente(Artesao cliente){
		String comando = "insert into clientes" + "(nome, endereco, telefone, sexo)" + "values(?,?,?,?)";
		PreparedStatement p;
		try{
			p = this.conexao.prepareStatement(comando);
			p.setString(1, cliente.getNome());
			p.setString(2, cliente.getEndereco());
			p.setString(3, cliente.getTelefone().replaceAll("[\\(.+ ?\\)-]", ""));
			p.setString(4, cliente.getSexo());
			p.execute();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Artesao> buscarPorNome(String nome){
		String comando = "Select * from clientes ";
		if(!nome.equals("null") && !nome.equals("")){
			comando += "where nome like'%" + nome + "%'";
		}

		List<Artesao> listCliente = new ArrayList<Artesao>();
		Artesao cliente = null;
		try{
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()){
				
				cliente = new Artesao();
				int id = rs.getInt("cod_cliente");
				String nomeCliente = rs.getString("nome");
				String ende = rs.getString("endereco");
				String telefone = rs.getString("telefone");
				String sexo = rs.getString("sexo");

				cliente.setId(id);
				cliente.setNome(nomeCliente);
				cliente.setEndereco(ende);
				cliente.setTelefone(telefone);
				cliente.setSexo(sexo);
				listCliente.add(cliente);
			}

		} catch(Exception e){
			e.printStackTrace();
		}
		return listCliente;
	}
	
	public boolean deletarCliente(int id){
		String comando = " delete from clientes where cod_cliente = " + id;
		Statement p;
		
		try{
			
			p = this.conexao.createStatement();
			
			p.execute(comando);
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Artesao buscarPorId(int cod){
	
		String comando = "Select * from clientes where cod_cliente = "+cod;
		Artesao cliente = new Artesao();
		try{
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()){
				int id = rs.getInt("cod_cliente");
				String nomeCliente = rs.getString("nome");
				String endereco = rs.getString("endereco");
				String telefone = rs.getString("telefone");
				
				cliente.setId(id);
				cliente.setNome(nomeCliente);
				cliente.setEndereco(endereco);
				cliente.setTelefone(telefone);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return cliente;
	}
	
	public boolean atualizar(Artesao cliente){
		
		String comando = "Update clientes set nome=?, endereco=?, telefone=?";
		
		PreparedStatement p;
		
		try{
			p = this.conexao.prepareStatement(comando);
			p.setString(1, cliente.getNome());
			p.setString(2, cliente.getEndereco());
			p.setString(3, cliente.getTelefone().replaceAll("[\\(.+ ?\\)-]", ""));
			
			System.out.println(p);
			
			p.executeUpdate();
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//Método de Login 
					  
	public boolean login(String usuario, String senha){
	
		//Criação da variável que irá receber o Select para busca no banco de dados
        String comando = "Select * from artesao.validacao where usuario= '"+ usuario +"' and senha= '"+ senha + "'";
		
        //Declaração do objeto us que está referênciando a Classe Usuario()
        Usuario us = new Usuario();
		
		try{
			//Criação do objeto stmt e atribuição da estrutura
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()){
				
				String user = rs.getString("usuario");
				String pass = rs.getString("senha");
				
				us.setUsuario(user);
				us.setSenha(pass);
			
			}
			
			//If que condiciona se o objeto us não está contido de valor e retorna false
			if(us.getUsuario() == null && 
				    us.getSenha() == null)
			{
				return false;
			}
			
			
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		return true;
	}
}