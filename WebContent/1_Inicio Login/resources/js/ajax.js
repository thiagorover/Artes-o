//Criando o objeto genérico SENAI

SENAI = new Object();
// Criando o sub-objeto ajax do objeto SENAI.
SENAI.ajax = new Object();
/*
 * Processa o pedido, solicitação HTTP Ajax a ser recebido pelo Rest.
 */
function ajaxRequestDefault(){
	var def = {
		url : null,
		dataType : 'json',
		contentType : "application/json; charset=UTF-8",
		type : 'POST',
		success : function() {
		},
		error : function(err) {
			alert("error = " + err.responseText);
		}
	};
	return def;
}

/*
 * verifica o estado do objeto cfg recebido, ou seja, se o identificador cfg
 * trata-se realmente de uma variável de objeto contendo seus respectivas
 * propriedades com valores, se isto se confirmar retorna o obejto cfg, dos
 * dados encontrados em cfg. Data em JSON para que posteriormente possam ser
 * repassados pelo Ajax a partir de um pedido HTTP. Notem também a chamada à
 * função isObject(cfg.data) que repassa como parâmetro cfg.data para ser os
 * dados apontados por cfg tratam-se de um array de dados, ou se é um objeto
 * simples, criado como {} ou new object e por último verifica se trata-se de
 * uma função objeto Javascript.
 */
function verifyObjectData(cfg) {
	if (cfg.data) {
		if (isObject(cfg.data)) {
			cfg.data = JSON.stringify(cfg.data);
		}
	}
	return cfg;
}

/*
 * A função abaixo verifica os dados apontados por cfg tratam-se de um array de
 * dados, ou se é um objeto simples, criado como {} ou new Object e por último
 * verifica se trata-se de uma função object e por estrutura passada pelo objeto
 * cfg.
 */
function isObject(o) {
	return $.isArray(o) | $.isPlainObject(o) | $.isFunction(o);
};

SENAI.ajax.post = function(cfg) {
	
	/*
	 * Inicia o Ajax e processa um pedido de Ajax, a partir de
	 * ajaxRequestDefault(). Esta inicialização e pedido são repassadas para o
	 * objeto def que, por sua vez, passará a ser um Ajax solicitante de uso
	 * geral.
	 */
	
	var def = new ajaxRequestDefault();

	/*
	 * Chama a função verifyObjectData() que, por sua vez, verifica o estado do
	 * objeto cfg recebido, ou seja, se o identificador cfg trata-se realmente
	 * de uma variável de objeto contendo suas respectivas propriedades com
	 * valores, se isto se confirmar retorna o obejto cfg e o armazena também em
	 * outro objeto cfg que será fundido no objeto config para que uma
	 * solicitação, pedido HTTP, Ajax seja enviada para um recurso Rest.
	 */
	
	cfg = verifyObjectData(cfg);
	
	/*
	 * Fundindo os objetos def e cfg e armazenando seus respectivos valores em
	 * config.
	 */
	
	var config = $.extend(def, cfg);
	
	/*
	 * Realizando um HTTP pedido ajax.Em o parâmetro config segue o pedido Ajax.
	 */
	
	$.ajax(config);

};

SENAI.ajax.get = function(cfg){
	var def = new ajaxRequestDefault();
	cfg.type = "GET";
	cfg = verifyObjectData(cfg);
	var config = $.extend(def, cfg);
	$.ajax(config);
};