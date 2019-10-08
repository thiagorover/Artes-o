valida = new Object();

var confirScript = "";
var retorno = true;

$(document).ready(function(){
	
	valida.login = function(){

		if(document.getElementById("usuario").value == ""){
			 confirScript = ("*Informe um login");
			 retorno = false;
		}
		if(document.getElementById("senha").value == ""){
			confirScript += ("*Informe uma senha");
			retorno = false;
		}
		
		if(retorno == false){
			
		var cfg = {
				title : "Mensagem",
				height : 250,
				width : 400,
				modal : true,
				buttons : {
					"Ok" : function() {
						$(this).dialog("close");
					}
				}
			};
			$("#msg").html(confirScript);
			$("#msg").dialog(cfg);

		return false;
		
		} else {
			
			
			$.ajax({

				type: "POST",
				url: "Login",
				data: $("#loginServlet").serialize(),
				success: function(msg){
					
					if(msg == ""){
						window.location.href = "index.html";
					} else {
						
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
						$("#msg").html(msg.msg);
						$("#msg").dialog(cfg);
						
					}

				},
				error: function(rest){

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
					$("#msg").html(msg.msg);
					$("#msg").dialog(cfg);
					
				}
			});
		}	//fim do if	retorno			
	};
});