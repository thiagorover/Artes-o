package br.com.artesao.rest;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import br.com.artesao.bd.conexao.Conexao;
import br.com.artesao.jdbc.JDBCArtesaoDAO;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Login extends HttpServlet{

	private static final long serialVersionUID = 1L;

	boolean recebe;

	public Login(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		process(request, response);
	}

	private Boolean process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		//Declaração o objeto sessao
		HttpSession sessao = request.getSession();
		sessao.setAttribute("login", request.getParameter("usuario"));

		String user = request.getParameter("usuario");
		String pass = request.getParameter("senha");

		try{

			//Criação da variável salt e inicialização da mesma com uma cadeia de caracteres
			String salt = "9e107d9d372bb6826bd81d3542a419d6";  
			String md5 = null;

			//Criptografa Md5 com Salt
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(pass.getBytes(),0,pass.length());
			md5 = new BigInteger(1, digest.digest()).toString(16);
			pass = md5 + salt;

			//Abertura do servidor
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCArtesaoDAO jdbcUsuario = new JDBCArtesaoDAO(conexao);
			recebe = jdbcUsuario.login(user, pass);
			conec.fecharConexao();

			Map<String, String> msg = new HashMap<String, String>();

			if (!recebe) {

				msg.put("msg", "Login incorreto!");

				String json = new Gson().toJson(msg);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);

				return false;
			} 

		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}