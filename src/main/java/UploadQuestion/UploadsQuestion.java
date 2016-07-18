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
 
@WebServlet(name = "uploadsquestion",urlPatterns = {"/UploadsQuestion/*"})
@MultipartConfig
public class UploadsQuestion extends HttpServlet {
 
 
  private static final long serialVersionUID = 2857847752169838915L;
  int BUFFER_LENGTH = 4096;
 
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
    	Connection conection = null;
	PreparedStatement stmt = null;
	PreparedStatement stmt3 = null;
	Statement stmt2 = null;
	ResultSet rs = null; 
	int idQuestao = 0;
	int id = 0;

	try {
			//id = Integer.parseInt(request.getParameter("idprova"));  
			//if(id == 0) getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    			InitialContext ic = new InitialContext();
    			Context initialContext = (Context) ic.lookup("java:comp/env");
    			DataSource datasource = (DataSource) initialContext.lookup("jdbc/MySQLDS");
    			conection = datasource.getConnection();
		
			stmt = conection.prepareStatement("insert into questao (enunciado,imagem,idprova) values (?,?,?)");
            			String texto = request.getParameter("des");
    			Part imagem = request.getPart("image");
			String type = imagem.getContentType();
	
    			InputStream input = imagem.getInputStream();
                			int size = input.available();
			byte dados[] = new byte[size];
			input.read(dados);	

			id = Integer.parseInt(request.getParameter("idprova"));  	
                			
			stmt.setString(1, texto);		
			stmt.setBytes( 2, dados );	
			stmt.setInt(3, id); 

			stmt.executeUpdate();        
			
			stmt2 = conection.createStatement();
			
			rs = stmt2.executeQuery("SELECT id FROM questao WHERE enunciado='"+texto + "'");
			if (rs.next()) {
				idQuestao = rs.getInt("id");  
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
			stmt.close();
			conection.close();
			
        			request.setAttribute("idProva", id);
        			getServletContext().getRequestDispatcher("/addQuestion.jsp").forward(request, response); 
		
	} catch (Exception ex) {
			response.getOutputStream().println("exception");
	}
    
  }
 
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 	
    }
}