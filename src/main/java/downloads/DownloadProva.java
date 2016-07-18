import java.io.*;
import java.sql.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;
import java.util.HashMap;
import br.com.ufpb.mathtimer.model.*;

import javax.sql.*;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "baixarprova",urlPatterns = {"/baixarprova/*"})
public class DownloadProva extends HttpServlet { 

	private static final long serialVersionUID = 2857847752169838915L;
  	int BUFFER_LENGTH = 4096;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getOutputStream().println("baixarprova");
    	}
 
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conection = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		Prova dadosAenviar = null;
 		ByteBuffer inputBB = new ByteBuffer(request.getInputStream());
  		ByteBuffer outputBB = null;
		int input = 0;

  		try {
    			ObjectInputStream ois = new ObjectInputStream(inputBB.getInputStream());
    			input = (Integer) ois.readObject();
  		}catch (Exception e) {
    			System.out.println(e);
    			e.printStackTrace();
  		}

		String query = "";
        		try {
			InitialContext ic = new InitialContext();
    			Context initialContext = (Context) ic.lookup("java:comp/env");
    			DataSource datasource = (DataSource) initialContext.lookup("jdbc/MySQLDS");
    			conection = datasource.getConnection();

			stmt = conection.createStatement();

			//criando a prova
			rs = stmt.executeQuery("SELECT * FROM prova WHERE id='"+ input + "'");
			if(rs.next()){
				dadosAenviar = new Prova(input, rs.getString("titulo"));
			}
	
			List<Questao> questoes = new ArrayList<Questao>();
			
			stmt2 = conection.createStatement();
			rs2 = stmt2.executeQuery("SELECT * FROM questao WHERE idprova='"+ input + "'");
			while(rs2.next()){
				Questao q = new Questao(rs2.getString("enunciado"));
				if(rs2.getBytes("imagem") != null){
					q.setTemImagem(true);
				}
				q.setImagem(rs2.getBytes("imagem"));
				
				List<Alternativa> alternativas = new ArrayList<Alternativa>();
				
				stmt3 = conection.createStatement();
				int idquestion = rs2.getInt("id");
				rs3 = stmt3.executeQuery("SELECT * FROM alternativa WHERE idquestao='"+ idquestion + "'");

				while(rs3.next()){
					Alternativa a = new Alternativa (rs3.getInt("id"), rs3.getString("texto"));
					alternativas.add(a);
					if(rs3.getString("certa").equals("s")){
						q.setAlternativaCorreta(a);
					}
				}
				q.adicionarAlternativas(alternativas);
				questoes.add(q);
			}
		
			dadosAenviar.adicionarQuestoes(questoes);
			
			rs3.close();
			rs2.close();
			rs.close();
			stmt3.close();
			stmt2.close();
			stmt.close();
			conection.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}

		try {

    			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    			ObjectOutputStream oos = new ObjectOutputStream(baos);

    			oos.writeObject(dadosAenviar);

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