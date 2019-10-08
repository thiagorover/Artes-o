package br.com.artesao.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.artesao.Validador.Validador;
import br.com.artesao.bd.conexao.Conexao;
import br.com.artesao.jdbc.JDBCArtesaoDAO;
import br.com.artesao.objetos.Artesao;


@Path("ArtesaoRest")
public class ArtesaoRest extends UtilRest {

	String recebe = "";

	public ArtesaoRest(){
	}

	@POST

	@Path("/addCliente")

	@Consumes("application/*")

	public Response addCliente(String artesaoParam){
		try{
			Artesao cliente = new ObjectMapper().readValue(artesaoParam, Artesao.class);

			Validador auxilio = new Validador(cliente);

			recebe = auxilio.valida(recebe);

			if(recebe == ""){
				
				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();
				JDBCArtesaoDAO jdbcArtesao = new JDBCArtesaoDAO(conexao);
				jdbcArtesao.addCliente(cliente);
				conec.fecharConexao();

				return this.buildResponse("Cliente cadastrado com sucesso");

			} else{

				return this.buildResponse(recebe);

			}

		} catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@POST
	@Path("/buscarClientePorNome/{nome}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarClientePorNome(@PathParam("nome") String nome){

		try {

			List<Artesao> clientes = new ArrayList<Artesao>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCArtesaoDAO jdbcArtesao = new JDBCArtesaoDAO(conexao);
			clientes = jdbcArtesao.buscarPorNome(nome);

			conec.fecharConexao();

			return this.buildResponse(clientes);

		} catch (Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@POST
	@Path("/deletarCliente/{id}")
	@Consumes("application/*")
	public Response deletarCliente(@PathParam("id") int id){

		try{
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCArtesaoDAO jdbcArtesao = new JDBCArtesaoDAO(conexao);
			jdbcArtesao.deletarCliente(id);
			conec.fecharConexao();

			return this.buildResponse("Cliente deletado com sucesso!");
		} catch(Exception e){
			e.printStackTrace();

			return this.buildErrorResponse(e.getMessage());
		}
	}

	@POST
	@Path("/buscarClientePeloId/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarClientePeloId(@PathParam("id") int id){

		try {

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCArtesaoDAO jdbcArtesao = new JDBCArtesaoDAO(conexao);
			Artesao cliente = jdbcArtesao.buscarPorId(id);

			return this.buildResponse(cliente);
		}catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@POST
	@Path("/editarCliente")
	public Response editarCliente(String clienteParam){
		try{
			Artesao cliente = new ObjectMapper().readValue(clienteParam, Artesao.class);

			Validador auxilio = new Validador(cliente);

			recebe = auxilio.valida(recebe);

			if(recebe == ""){

				Conexao conec = new Conexao();
				Connection conexao = conec.abrirConexao();
				JDBCArtesaoDAO jdbcArtesao = new JDBCArtesaoDAO(conexao);
				jdbcArtesao.atualizar(cliente);
				conec.fecharConexao();

				return this.buildResponse("Cliente editado com sucesso!");
			} else {
				return this.buildResponse(recebe);
			}

		} catch(Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}