import java.io.*;
import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
 
import javax.sql.*;
import javax.naming.*;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
 
@WebServlet(name = "uploadsprova",urlPatterns = {"/UploadsProva/*"})
@MultipartConfig
public class UploadsQuestion extends HttpServlet {
 
 
  private static final long serialVersionUID = 2857847752169838915L;
  int BUFFER_LENGTH = 4096;
 
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	Connection conection = null;
	PreparedStatement stmt = null;
	PreparedStatement stmt2 = null;
	PreparedStatement stmt3 = null;
	ResultSet rs = null; 
	int id = 0;
	int idQuestao = 0;
	String texto = "";

	try {
    			InitialContext ic = new InitialContext();
    			Context initialContext = (Context) ic.lookup("java:comp/env");
    			DataSource datasource = (DataSource) initialContext.lookup("jdbc/MySQLDS");
    			conection = datasource.getConnection();
		
			
			id = Integer.parseInt(request.getParameter("idprova"));  
			if(id == 0){
				stmt = conection.prepareStatement("insert into prova (titulo) values (?)", Statement.RETURN_GENERATED_KEYS);
            			texto = request.getParameter("id");
    			
				stmt.setString(1, texto);
				stmt.executeUpdate(); 

				rs = stmt.getGeneratedKeys();  
    				if(rs.next()){  
        				id = rs.getInt(1);  
    				}     
				stmt.close();
			}else{
				texto = request.getParameter("nomeprova");
			}
    			
			stmt2 = conection.prepareStatement("insert into questao (enunciado,imagem,idprova) values (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            		String texto = request.getParameter("des");
    			Part imagem = request.getPart("image");
			String type = imagem.getContentType();
	
    			InputStream input = imagem.getInputStream();
                			int size = input.available();
			byte dados[] = new byte[size];
			input.read(dados);	

			id = Integer.parseInt(request.getParameter("idprova"));  	
                			
			stmt2.setString(1, texto);		
			stmt2.setBytes( 2, dados );	
			stmt2.setInt(3, id); 

			stmt2.executeUpdate();        

			rs = stmt2.getGeneratedKeys();  
    			if(rs.next()){  
        			idQuestao = rs.getInt(1);  
    			} 

			String alternativas[] = request.getParameterValues("alt");
			List<String> listaDeAlternativas = new ArrayList<String>(Arrays.asList(alternativas));

			for(int i = 1; i <= 4; i++){
				stmt3 = conection.prepareStatement("insert into alternativa (texto, certa,idquestao) values (?,?,?)");

				String alternativa = "alt" + i;

				stmt3.setString(1, request.getParameter(alternativa) );	

				String certa = "";
				if(listaDeAlternativas.contains(alternativa)){
					certa = "s";
				}else{
					certa = "n";
				}
				stmt3.setString( 2, certa );	
				stmt3.setInt(3, idQuestao);
				stmt3.executeUpdate();       
			}
			 
			rs.close(); 
			stmt3.close(); 
			stmt2.close(); 
			//stmt.close();
			conection.close();			
			
        		request.setAttribute("idProva", id );
			request.setAttribute("nomeProva", texto );

			getServletContext().getRequestDispatcher("/teste.jsp").forward(request, response); 
			
	} catch (Exception ex) {
			response.getOutputStream().println("exception");
	}
   
  }
 
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 	    }
}