<html> 
<head>
	<!-- Dependencies -->
		<script src="jquery.js" type="text/javascript"></script>
		<script src="jquery.ui.draggable.js" type="text/javascript"></script>

		<!-- Core files -->
		<script src="jquery.alerts.js" type="text/javascript"></script>
		<link href="jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
		<link rel="stylesheet" type="text/css" href="btn.css">

</head>
	<body onLoad="checaProva()"> 
		<%
  			int idProva;
			String nomeProva = "";
			if(request.getAttribute("idProva") == null){
				idProva = 0;
			}
			else{
				idProva = (Integer)request.getAttribute("idProva");
			}

			if(request.getAttribute("nomeProva") != null){
				nomeProva = request.getAttribute("nomeProva");
			}

		%>
		<script language="Javascript">
			function valida(){
			<!--
				var nomedaProva = document.inserirprova.id.value;
				var enunciado = document.inserirprova.des.value;
				var alernativa1 = document.inserirprova.alt1.value;
				var alernativa2 = document.inserirprova.alt2.value;
				var alernativa3 = document.inserirprova.alt3.value;
				var alernativa4 = document.inserirprova.alt4.value;
				var alernativa5 = document.inserirprova.alt5.value;
				var qtd = 0;
				if(alernativa1==""){
					qtd = qtd + 1;
				}
				if(alernativa2==""){
					qtd = qtd + 1;
				}
				if(alernativa3==""){
					qtd = qtd + 1;
				}
				if(alernativa4==""){
					qtd = qtd + 1;
				}
				if(alernativa5==""){
					qtd = qtd + 1;
				}
				if (nomedaProva==""){
					jAlert("É necessário o preenchimento do nome da prova", "Notificação");
					document.inserirprova.id.focus();
					return false;
				}else if (enunciado==""){
					jAlert("O campo enunciado não pode ficar em branco.", "Notificação");
					document.inserirprova.des.focus();
					return false;
				}else if (qtd>3){
					jAlert("É necessário o preenchimento de pelo menos duas alternativas", "Notificação");
					document.inserirprova.alt1.focus();
					return false;
				}else if (document.inserirprova.id1.checked==false &&
					document.inserirprova.id2.checked==false &&
					document.inserirprova.id3.checked==false &&
					document.inserirprova.id4.checked==false &&
					document.inserirprova.id5.checked==false){
						jAlert("Você deve selecionar pelo menos uma alternativa", "Notificação");
						return false;
				}else if(document.inserirprova.id1.checked==true && alernativa1==""){
					jAlert("Alternativa selecionada incorretamente", "Notificação");
					return false;
				}else if(document.inserirprova.id2.checked==true && alernativa2==""){
					jAlert("Alternativa selecionada incorretamente", "Notificação");
					return false;
				}else if(document.inserirprova.id3.checked==true && alernativa3==""){
					jAlert("Alternativa selecionada incorretamente", "Notificação");
					return false;
				}else if(document.inserirprova.id4.checked==true && alernativa4==""){
					jAlert("Alternativa selecionada incorretamente", "Notificação");
					return false;
				}else if(document.inserirprova.id5.checked==true && alernativa5==""){
					jAlert("Alternativa selecionada incorretamente", "Notificação");
					return false;
				}
			}

			function checaProva(){
				var prova = '<%= nomeProva %>';
				if(prova != null){
					document.inserirprova.id.value = prova;
					document.inserirprova.id.readOnly = true;
				}
			}
			//-->
		</script>

		<h1>Página para adicionar as questões: </h1>
		<form action="/UploadsProva" enctype="multipart/form-data" method="post" name="inserirprova" onsubmit="return valida();">
			<input type="hidden" name="idprova" value="<%= idProva%>"  /> <br/>
			<input type="hidden" name="nomeprova" value="<%= nomeProva%>"  /> <br/>
			<h2>Nome da prova</h2> <br/>
			Título da prova <input type="text" class="margem inputtexto" name="id"/> <br/> <br/>
			<hr>
			<h2>Dados da questão</h2> <br/>
			Enunciado da questão <textarea class="margem inputtexto alinhamento" name="des" rows="3" cols="50"/></textarea><br/>
			Selecionar imagem <div class="fileUpload bt btn-primary"> <span>Selecionar</span> <input type="file" name="image" class="upload"/> </div><br/> <br/>
			<hr>
			Lista de alternativas: <br/> <br/>
			Alternativa 1: <textarea class="margem inputtexto alinhamento" name="alt1" rows="3" cols="50"/></textarea> <br/><br/>
			Alternativa 2: <textarea class="margem inputtexto alinhamento" name="alt2" rows="3" cols="50"/></textarea> <br/><br/>
			Alternativa 3: <textarea class="margem inputtexto alinhamento" name="alt3" rows="3" cols="50"/></textarea> <br/><br/>
			Alternativa 4: <textarea class="margem inputtexto alinhamento" name="alt4" rows="3" cols="50"/></textarea> <br/><br/>
			Alternativa 5: <textarea class="margem inputtexto alinhamento" name="alt5" rows="3" cols="50"/></textarea> <br/><br/>
			<hr>
			Marque a alternativa correta: <br/> <br/>
			<input type="checkbox" class="check" id="id1" name="alt" value="alt1"/> Alternativa 1 <br/> 
			<input type="checkbox" id="id2" name="alt" value="alt2"/> Alternativa 2 <br/> 
			<input type="checkbox" id="id3" name="alt" value="alt3"/> Alternativa 3 <br/> 
			<input type="checkbox" id="id4" name="alt" value="alt4"/> Alternativa 4 <br/>
			<input type="checkbox" id="id5" name="alt" value="alt5"/> Alternativa 5 <br/> <br/> 
			<input type="submit" class="botao" value="Salvar questão"/> 
		</form> 
	</body> 
</html> 