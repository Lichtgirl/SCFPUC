package br.edu.ufam.scfpcu.reports;
/*
 * ConexaoDbCodBarras.java
 *
 * Created on 4 de Julho de 2007, 09:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jboss.seam.annotations.Name;



/**
 * Classe para se conectar ao banco
 * 
 * @author asvieira
 */
@Name("ConnectionJDBC")

public class ConnectionJDBC {
	private static ConnectionJDBC instancia = null;
	private String url = null;
	private String driver = null;
	private String user = null;
	private String psw = null;
	private boolean connected;

	/**
	 * Componente necessario para manipula\u00E7\u00E3o de dados
	 */
	protected Connection conection = null;

//	private void setAtributesForXml() {
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		HttpServletRequest request = (HttpServletRequest) facesContext
//				.getExternalContext().getRequest();
//		String xmlUrlReal = request.getRealPath("/../../scrp-dev-ds.xml");
//
//		File file = new File(xmlUrlReal);
//
//		try {
//			DocumentBuilderFactory factory = DocumentBuilderFactory
//					.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document document = builder.parse(file);
//
//			Element element = document.getDocumentElement();
//			//NodeList connection_url = element.getElementsByTagName("connection-url");
//			NodeList driver_class = element.getElementsByTagName("driver-class");
//			NodeList user_name = element.getElementsByTagName("user-name");
//			NodeList password = element.getElementsByTagName("password");
//
//			//Element connectionUrl = (Element)connection_url.item(0);
//			Element driverClass = (Element) driver_class.item(0);
//			Element userName = (Element) user_name.item(0);
//			Element psw = (Element) password.item(0);
//
//			//this.url = connectionUrl.getFirstChild().getNodeValue();
//			this.driver = driverClass.getFirstChild().getNodeValue();
//			this.user = userName.getFirstChild().getNodeValue();
//			this.psw = psw.getFirstChild().getNodeValue();
//
//		} catch (ParserConfigurationException e) {
//
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/**
	 * Creates a new instance of ConexaoDbCodBarras
	 */
	protected ConnectionJDBC(String ano) {
//		this.setAtributesForXml();	
//		this.setBaseporAno(ano);
		try {
			Class.forName(this.driver);
			this.conection = DriverManager.getConnection(this.url, this.user, this.psw);
			this.setConnected(true);
			System.out.println("Connectado: "+ano);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			this.setConnected(false);
		} catch (SQLException e) {
			e.printStackTrace();
			this.setConnected(false);
		}
	}
	
//	public void setBaseporAno(String ano){
//		this.driver = "com.informix.jdbc.IfxDriver";
//		Tbano tbano=new TbanoHome().getTbano(ano);
//		this.url = tbano.getUrl();
//		this.user =tbano.getUsr();
//		this.psw = tbano.getPsw();
//	}

	public static ConnectionJDBC getInstancia(String anoBase){
		instancia = new ConnectionJDBC(anoBase);
		return instancia;
    }

	public void resetaConexao() {
		instancia = null;
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
