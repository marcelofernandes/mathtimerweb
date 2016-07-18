import java.io.*;
import java.sql.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.HashMap;

import javax.sql.*;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "checkprova",urlPatterns = {"/checkprova/*"})
public class CheckProvas extends HttpServlet { 

	private static final long serialVersionUID = 2857847752169838915L;
  	int BUFFER_LENGTH = 4096;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		//response.getOutputStream().println("checkprovas");
    	}
 
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		
		Connection conection = null;
		Statement stmt = null;
		ResultSet rs = null;
		HashMap<String,Integer> dadosAenviar = new HashMap<String,Integer>();

		ByteBuffer inputBB = new ByteBuffer(request.getInputStream());
  		ByteBuffer outputBB = null;
		List<Integer> input = null;

  		try {

    			ObjectInputStream ois = new ObjectInputStream(inputBB.getInputStream());
    			input = (List<Integer>) ois.readObject();

  		}catch (Exception e) {
    			System.out.println(e);
    			e.printStackTrace();
  		}

        		try {
			InitialContext ic = new InitialContext();
    			Context initialContext = (Context) ic.lookup("java:comp/env");
    			DataSource datasource = (DataSource) initialContext.lookup("jdbc/MySQLDS");
    			conection = datasource.getConnection();

    			stmt = conection.createStatement() ;

			String query = "Select * from prova where  id not in (" + input.get(0);

			for( int i = 1; i < input.size(); i++ ){
				query = query + "," + input.get(i);		
			}
			dadosAenviar.clear();
			
			query = query + ")";
		
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				dadosAenviar.put( rs.getString("titulo"), rs.getInt("id") );  
			}
			
			rs.close();
			stmt.close();
			conection.close();
		}
		catch (Exception ex){
		}

		try {

    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			ObjectOutputStream oos = new ObjectOutputStream(baos);

    			oos.writeObject((Serializable)dadosAenviar);

    			outputBB = new ByteBuffer(baos.toByteArray());

  		}catch (Exception e) {
    			System.out.println(e);
    			e.printStackTrace();
  		}

		ServletOutputStream sos = response.getOutputStream();

    		response.setContentType("application/octet-stream");
    		response.setContentLength(outputBB.getSize());
    		sos.write(outputBB.getBytes());
  		
  		sos.flush();
  		sos.close();
		
        	}
}