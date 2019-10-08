SENAI.cliente = new Object();

$(document).ready(function() {
	$("#telefone").mask("(99) 9999-9999");
});

$(document).ready(function() {
	$("#telefoneEdit").mask("(99) 9999-9999");
});


$(document).ready(function(){

	SENAI.cliente.cadastrar = function(){

		var newClient = new Object();
		newClient.id = $("#idUser").val();
		newClient.nome = $("#nome").val();
		newClient.endereco = $("#endereco").val();
		newClient.telefone = $("#telefone").val();
		newClient.sexo = $("#sexo").val();

		var cfg = {

				url:"rest/ArtesaoRest/addCliente",
				data: newClient,
				success: function(msg){
					var cfg = {

							title: "Mensagem",
							height: 250,
							width: 400,
							modal: true,
							buttons: {
								"Ok": function(){
									$(this).dialog("close");
								}
							}
					};
					$("#msg").html(msg);
					$("#msg").dialog(cfg);

					SENAI.cliente.buscar();
				},

				error: function(){

					var cfg = {
							title: "Mensagem",
							height: 250,
							width: 400,
							modal: true,
							buttons: {
								"Ok": function(){
									$(this).dialog("close");
								}
							}
					};
					$("#msg").html("Erro ao cadastrar um novo contato!"+err.responseText);
					$("#msg").dialog(cfg);

					SENAI.cliente.buscar();
				}
		};

		SENAI.ajax.post(cfg);
	};

	SENAI.cliente.buscar = function(){

		var valorBusca = $("#consultarCliente").val();

		SENAI.cliente.exibirClientes(undefined, valorBusca);

	};

	SENAI.cliente.exibirClientes = function(listaDeClientes, valorBusca){

		var html = "<table class='table'>";
		html += "<tr><th>Nome: </th><th>Endereço: </th><th>Telefone: </th></tr>";
		if(listaDeClientes != undefined
				&& listaDeClientes.length > 0
				&& listaDeClientes[0].id != undefined){
			for(var i = 0; i < listaDeClientes.length; i++){
				html += "<tr>"
					+ "<td>" + listaDeClientes[i].nome + "</td>"
					+ "<td>" + listaDeClientes[i].endereco + "</td>"
					+ "<td>" + listaDeClientes[i].telefone + "</td>"
					+ "<td>"
					+ "<a class='link' onclick='SENAI.cliente.editarCliente("+ listaDeClientes[i].id +")'>Editar</a>"
					+ "<a class='link' onclick='SENAI.cliente.confirmarDelete("+ listaDeClientes[i].id +")'>Deletar</a>" + "</td>" + "</tr>"; 
			}//Fecha o for
		} else {
			if(listaDeClientes == undefined || (listaDeClientes != undefined && listaDeClientes.length > 0)){
				if(valorBusca == ""){
					valorBusca = null;
				}

				var cfg = {
						type : "POST",
						url: "rest/ArtesaoRest/buscarClientePorNome/" + valorBusca,
						success: function(listaDeClientes){
							SENAI.cliente.exibirClientes(listaDeClientes);
						},
						error: function(err){

							var cfg = {
									title: "Mensagem",
									height: 250,
									width: 400,
									modal: true,
									buttons : {
										"Ok": function(){
											$(this).dialog("close");
										}
									}			
							};
							$("#msg").html("Erro ao consultar o cliente! " + err.responseText);
							$("#msg").dialog(cfg);
						}
				};
				SENAI.ajax.post(cfg);
			}else{
				html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
			}
		}
		html += "</table>";
		$("#resultadoCliente").html(html);
	};
	SENAI.cliente.exibirClientes(undefined, "");

	SENAI.cliente.deletarCliente = function(id){

		var cfg = {

				type: "POST",
				url: "rest/ArtesaoRest/deletarCliente/"+id,
				success: function(msg){
					var cfg = {
							title: "Mensagem",
							height: 250,
							width: 400,
							modal: true,
							buttons : {
								"OK": function(){
									$(this).dialog("close");
								}
							}
					};
					$("#msg").html(msg);
					$("#msg").dialog(cfg);

					SENAI.cliente.buscar();
				},
				error: function(err){
					var cfg = {
							title: "Mensagem",
							height: 350,
							width: 400,
							modal: true,
							buttons: {
								"OK": function(){
									$(this).dialog("close");
								}
							}
					};
					$("#msg").html("Erro ao deletar o cliente!"+ err.responseText);
					$("#msg").dialog(cfg);
				}
		};
		SENAI.ajax.post(cfg);
	};

	SENAI.cliente.confirmarDelete = function(id){

		var cfg = {
				title: "Mensagem",
				height: 250,
				width: 400,
				modal: true,
				buttons: {
					"Sim" : function(){
						SENAI.cliente.deletarCliente(id);
					},

					"Não" : function(){
						$(this).dialog("close");
					}
				}
		};
		$("#msg").html("Gostaria de deletar o Cliente? ");
		$("#msg").dialog(cfg);
	}
	
	SENAI.cliente.editarCliente = function(id){
		var cfg = {
				type: "POST",
				url: "rest/ArtesaoRest/buscarClientePeloId/"+ id,
				success: function(cliente){
					$("#idClienteEdit").val(cliente.id);
					$("#nomeEdit").val(cliente.nome);
					$("#enderecoEdit").val(cliente.endereco);
					$("#telefoneEdit").val(cliente.telefone);
					
					SENAI.cliente.exibirEdicao(cliente);
				},

				error: function(err){
					var cfg = {
							title: "Mensagem",
							height: 250,
							width: 400,
							modal: true,
							buttons: {
								"OK" : function(){
									$(this).dialog("close");
								}
							}
					};
					$("#msg").html("Erro ao editar o cliente! " + err.responseText);
					$("#msg").dialog(cfg);
				}
		};
		SENAI.ajax.post(cfg);
	};

	SENAI.cliente.exibirEdicao = function(cliente){

		var cfg = {
				title: "Editar Cliente",
				height: 300,
				width: 400,
				modal: true,
				buttons : {
					"Salvar" : function(){
						var dialog = this;
						var newCliente = new Object();
						newCliente.id = $("#idClienteEdit").val();
						newCliente.nome = $("#nomeEdit").val();
						newCliente.endereco = $("#enderecoEdit").val();
						newCliente.telefone = $("#telefoneEdit").val();
						console.log(JSON.stringify(newCliente));
						
						//Variáveis para válidação
						var retorna = "";
						var protecao = true;

						//Validação Expressão regular

						var nomeEdit = $("#nomeEdit").val();
						var enderecoEdit = $("#enderecoEdit").val();
						var telefoneEdit = $("#telefoneEdit").val();

						var reg1x = /[A-Za-z]+$/;
						var reg2x = /\(?[0-9]{2}\)? ?[0-9]{4}-?[0-9]{4}/;

						//Validações de Expressão Regular

						if(!nomeEdit.match(reg1x)){
							retorna += ("O campo nome não deve conter números.<br>");
							protecao = false;
						}

						if(!telefoneEdit.match(reg2x)){
							retorna += ("O campo telefone deve conter números.<br>");
							protecao = false;
						}

						//Validações de Espaços

						if($("#nomeEdit").val() == ""){
							retorna += ("O campo nome é obrigatório!<br>");
							protecao = false;
						}

						if($("#enderecoEndit").val() == ""){
							retorna += ("O campo endereço é obrigatório!<br>");
							protecao = false;
						}

						if($("#telefoneEdit").val() == ""){
							retorna += ("O campo telefone é obrigatório!<br>");
							protecao = false;
						}
						
						if(protecao){

							var cfg = {
									type: "POST",
									url: "rest/ArtesaoRest/editarCliente",
									data: newCliente,
									success: function(msg){

										var cfg = {
												title: "Mensagem",
												height: 250,
												width: 400,
												modal: true,
												buttons: {
													"Ok": function(){
														$(this).dialog("close");
													}
												}
										};
										$("#msg").html(msg);
										$("#msg").dialog(cfg);
										
										SENAI.cliente.buscar();

										$(dialog).dialog("close");

									},

									error: function(err){

										var cfg = {
												title: "Mensagem",
												height: 250,
												width: 400,
												modal: true,
												buttons: {
													"Ok": function(){
														$(this).dialog("close");
													}
												}
										};
										$("#msg").html("Erro ao editar o cliente!" + err.responseText);
										$("#msg").dialog(cfg);

										$(dialog).dialog("close");
									}
							};
							SENAI.ajax.post(cfg);
						} else {

							//Modal de erro

							var cfg = {
									title: "Mensagem",
									height: 250,
									width: 400,
									modal: true,
									buttons: {
										"Ok": function(){
											$(this).dialog("close");
										}
									}
							};
							$("#msg").html(retorna);
							$("#msg").dialog(cfg);
							
						}
					},
					"Cancelar": function(){
						$(this).dialog("close");
					}
				},

				close : function() {
				}
		};

		$("#editarCliente").dialog(cfg);
	};

}); //Fecha o método