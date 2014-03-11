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
import org.xml.sax.SAXException;

public class ConnectionJDBC {

	private static ConnectionJDBC instancia = null;
    private String url=null;
    private String driver=null;
    private String user=null;
    private String psw=null;
    private boolean connected;
   
    
    protected Connection conection=null;
    
    private void setAtributesForXml(){
    	
    	FacesContext facesContext = FacesContext.getCurrentInstance();  
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();  
		String xmlUrlReal = request.getRealPath("/resources/scfpcu-dev-ds.xml");
    	
    	File file = new File(xmlUrlReal);

    	try {
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
			this.psw = psw.getFirstChild().getNodeValue();
			
		} catch (ParserConfigurationException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Creates a new instance of ConexaoDbCodBarras
     */
    protected ConnectionJDBC() {
        try {
            
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
    }
    
    public static ConnectionJDBC getInstancia(){
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
