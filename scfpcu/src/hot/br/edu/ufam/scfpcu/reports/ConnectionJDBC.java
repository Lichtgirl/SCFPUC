package br.edu.ufam.scfpcu.reports;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;
import org.xml.sax.SAXException;

public class ConnectionJDBC {

	private static ConnectionJDBC instancia = null;
    private String url=null;
    private String driver=null;
    private String user=null;
    private String psw=null;
    private boolean connected;
   
    
    protected Connection conection=null;
    
    @SuppressWarnings("deprecation")
	private void setAtributesForXml(){
    	
    	FacesContext facesContext = FacesContext.getCurrentInstance();  
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();  
		String xmlUrlReal = request.getRealPath("../../scfpcu-ds.xml");
		
    	System.out.println("olha ai adheli ");
    	File file = new File(xmlUrlReal);
    	
    
		
    	
    	System.out.println("xmlUrlReal:::::"+xmlUrlReal);
    	try {
    		
    		System.out.println("entrei no try connection");
    		
    		
    		
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = factory.newDocumentBuilder();
    		Document document = builder.parse(file);    	
    		
    		
			Element element = document.getDocumentElement();
			NodeList connection_url = element.getElementsByTagName("connection-url");
			NodeList driver_class = element.getElementsByTagName("driver-class");
			NodeList user_name = element.getElementsByTagName("user-name");
			NodeList password = element.getElementsByTagName("password");
			
			Element connectionUrl = (Element)connection_url.item(0);
			Element driverClass = (Element)driver_class.item(0);
			Element userName = (Element)user_name.item(0);
			Element psw = (Element)password.item(0);
			
			
			
			
			
			this.url = connectionUrl.getFirstChild().getNodeValue();
			this.driver = driverClass.getFirstChild().getNodeValue();
			this.user = userName.getFirstChild().getNodeValue();
			this.psw = "";
			
			System.out.println("URL   "+url);
			System.out.println("Username     "+user);
			System.out.println("Psswd ;;;;;;;"+psw);
			
			System.out.println("to aqui depois do pswd");
		} catch (ParserConfigurationException e) {
			System.out.println("entrei no catch conexao");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("entrei no catch sax");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("entrei no catch IO");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  System.out.println("sai do parser XML");
    }
    
    /**
     * Creates a new instance of ConexaoDbCodBarras
     */
    protected ConnectionJDBC() {
        try {
            
        	System.out.println("entrei no conect jdbc");
        	System.out.println(url);
            this.setAtributesForXml();
            Class.forName(driver);
            conection = DriverManager.getConnection(url,user,psw);
            this.setConnected(true);
        } catch(ClassNotFoundException e){
            e.printStackTrace();
            this.setConnected(false);
        } catch (SQLException e) {
        	e.printStackTrace();
        	this.setConnected(false);
        }
        System.out.println("sai do Connection JDBC");
    }
    
    public static ConnectionJDBC getInstancia(){
    	System.out.println("Entrei no getinstance");
        if (instancia == null){
            instancia = new ConnectionJDBC();
        }
        return instancia;
    }

    
    public void resetaConexao(){
        instancia=null; 
    }

    public Connection getConection() {
        return conection;
    }

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
    
	
	
	
	
}
