<html> 
	<body> 
		<%
  			int idProva;
			if(request.getAttribute("idProva")==null)
				idProva = 0;
			else
				idProva = (Integer)request.getAttribute("idProva");
		%>
		<h1>Página para adicionar questao: </h1><br/><br/>
		<form action="/UploadsQuestion" enctype="multipart/form-data" method="post">
			<input type="hidden" name="idprova" value="<%= idProva%>"  /> <br/>
			Enunciado da questão <input type="text" name="des"/> <br/>
			Selecionar imagem <input type="file" name="image"/> <br/> <br/>
			Lista de alternativas: <br/>
			Alternativa 1: <input type="text" name="alt1"/> <br/>
			Alternativa 2: <input type="text" name="alt2"/> <br/>
			Alternativa 3: <input type="text" name="alt3"/> <br/>
			Alternativa 4: <input type="text" name="alt4"/> <br/> <br/>
			Marque a alternativa correta: <br/>
			<input type="checkbox" name="alt" value="alt1"/> Alternativa 1 <br/> 
			<input type="checkbox" name="alt" value="alt2"/> Alternativa 2 <br/> 
			<input type="checkbox" name="alt" value="alt3"/> Alternativa 3 <br/> 
			<input type="checkbox" name="alt" value="alt4"/> Alternativa 4 <br/> <br/> 
			<input type="submit" value="Enviar"/> 
		</form> 
	</body> 
</html> 