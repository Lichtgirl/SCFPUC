package br.edu.ufam.scfpcu.reports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionJDBC {

	private static ConnectionJDBC instancia = null;
    private String url="jdbc:mysql://localhost/scfpcu";
    private String driver="com.mysql.jdbc.Driver";
    private String user="root";
    private String psw="";
    private boolean connected;
   
    
    protected Connection conection=null;
    
    @SuppressWarnings("deprecation")
//	private void setAtributesForXml(){
//    	
//    	FacesContext facesContext = FacesContext.getCurrentInstance();  
//		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();  
//		String xmlUrlReal = request.getRealPath("../../scfpcu-ds.xml");
//		
//    	System.out.println("olha ai adheli ");
//    	File file = new File(xmlUrlReal);
//    	
//    
//		
//    	
//    	System.out.println("xmlUrlReal:::::"+xmlUrlReal);
//    	try {
//    		
//    		System.out.println("entrei no try connection");
//    		
//    		
//    		
//    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//    		System.out.println("factoryyy;;;;"+factory);
//    		DocumentBuilder builder = factory.newDocumentBuilder();
//    		System.out.println("builder;;;;"+builder);
//    		Document document = builder.parse(file);    	
//    		
//    		
//			Element element = document.getDocumentElement();
//			NodeList connection_url = element.getElementsByTagName("connection-url");
//			NodeList driver_class = element.getElementsByTagName("driver-class");
//			NodeList user_name = element.getElementsByTagName("user-name");
//			NodeList password = element.getElementsByTagName("password");
//			
//			Element connectionUrl = (Element)connection_url.item(0);
//			Element driverClass = (Element)driver_class.item(0);
//			Element userName = (Element)user_name.item(0);
//			Element psw = (Element)password.item(0);
//			
//			
//			
//			
//			
//			this.url = connectionUrl.getFirstChild().getNodeValue();
//			this.driver = driverClass.getFirstChild().getNodeValue();
//			this.user = userName.getFirstChild().getNodeValue();
//			this.psw = "";
//			
//			System.out.println("URL   "+url);
//			System.out.println("Username     "+user);
//			System.out.println("Psswd ;;;;;;;"+psw);
//			
//			System.out.println("to aqui depois do pswd");
//		} catch (ParserConfigurationException e) {
//			System.out.println("entrei no catch conexao");
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SAXException e) {
//			System.out.println("entrei no catch sax");
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.out.println("entrei no catch IO");
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	  System.out.println("sai do parser XML");
//    }
    
    /**
     * Creates a new instance of ConexaoDbCodBarras
     */
    protected ConnectionJDBC() {
    	System.out.println("entrei no conect jdbc");
    	try {   
        	
//            this.setAtributesForXml();
            Class.forName(driver);
            this.conection = DriverManager.getConnection(url,user,psw);
            this.setConnected(true);
            System.out.println("Conectado");
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
    	
    	instancia = new ConnectionJDBC();
		return instancia;
    }

    
    public void resetaConexao(){
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
