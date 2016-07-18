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
 
@WebServlet(name = "uploads",urlPatterns = {"/uploads/*"})
@MultipartConfig
public class Uploads extends HttpServlet {
 
 
  private static final long serialVersionUID = 2857847752169838915L;
  int BUFFER_LENGTH = 4096;
 
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
    	Connection conection = null;
	PreparedStatement stmt = null;
	Statement stmt2 = null;
	ResultSet rs = null; 
	int id = 0;

	try {
    			InitialContext ic = new InitialContext();
    			Context initialContext = (Context) ic.lookup("java:comp/env");
    			DataSource datasource = (DataSource) initialContext.lookup("jdbc/MySQLDS");
    			conection = datasource.getConnection();
		
			stmt = conection.prepareStatement("insert into prova (titulo) values (?)");
            			String texto = request.getParameter("id");
    			
			stmt.setString(1, texto);
			stmt.executeUpdate();        
			
			stmt2 = conection.createStatement();
			rs = stmt2.executeQuery("SELECT id FROM prova WHERE titulo='"+texto + "'");
			
			if (rs.next()) {
				id = rs.getInt("id");  
			}
			
			rs.close(); 
			stmt2.close(); 
			stmt.close();
			conection.close();
			
        			request.setAttribute("idProva", id );
        			getServletContext().getRequestDispatcher("/addQuestion.jsp").forward(request, response); 
			
	} catch (Exception ex) {
			response.getOutputStream().println("exception");
	}
    
  }
 
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 	
    }
}