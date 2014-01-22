package br.edu.ufam.scfpcu.reports;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.core.Manager;
import org.jboss.seam.pdf.DocumentData;
import org.jboss.seam.pdf.DocumentStore; 
import org.jboss.seam.pdf.DocumentData.DocType;
import org.jboss.seam.faces.FacesMessages;





@Name("ReportAction")

public abstract class ReportAction{

	@In
	private Manager manager;
	
	@In
	FacesMessages facesMessages;
	
	@In(value = "org.jboss.seam.pdf.documentStore", create = true)  
	DocumentStore documentStore;
	
	private String error;
	private String anoBase="";
	
	public JRBeanCollectionDataSource getBeanCollectionDataSource(){
		return null;
	}
	
	protected abstract String getReportPath();
	protected abstract Boolean getAnoBaseIsSelected();
	
	protected abstract boolean canCreateReport();
	
	protected abstract Map<String, Object> getParams();
	
	@SuppressWarnings("deprecation")
	public String createPdfReport(){
		    
		String reportUrl = getReportPath(); 

		ConnectionJDBC conn = null;
		
		try {  
			
			Map<String, Object> params = new HashMap<String, Object>();  
			
			String anoBase=getAnoBase();
			if(anoBase.isEmpty()){
				this.error = "Não foi possivel gerar o relatorio.(Ano da base de dados vazio.)";
				return null;
			}

			 conn = ConnectionJDBC.getInstancia(anoBase);
			if(!conn.isConnected()){
				this.error = "Não foi possível criar conexão com o servidor";
				return null;
			}
			
			// obter os parâmetros específicos do relatório  
			params.putAll(getParams());  

			// obter o caminho real (file system) para o relatório  
			FacesContext facesContext = FacesContext.getCurrentInstance();  
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();  
			String reportUrlReal = request.getRealPath(reportUrl);  
			

			
			// imprimir o relatório para um stream em PDF  
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportUrlReal, params, conn.getConection());  
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
						
			// armazenar o relatório do DocumentStore do SEAM  
			String reportId = documentStore.newId();  
			DocumentData data = new DocumentData("Report #" + reportId , DocType.PDF, output.toByteArray());
			documentStore.saveData(reportId, data);
		
			// retornar para o caminho padrão do SEAM para exibição de relatórios  
			
			String fileName = "/seam-doc?docId=" + reportId;
			
			HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
			response.setHeader("Content-Disposition", "attachment; filename="+fileName);
			
			return fileName;
			       
		} catch (Exception e) {
			this.error = "Não foi possível gerar o relatório";
			e.printStackTrace();
			return null;  
		}  	
	}
		
	public String createPdfReport2(JRDataSource DS){
	    
		String reportUrl = getReportPath();		
		try {  			
			Map<String, Object> params = new HashMap<String, Object>();  
			
					
			// obter os parâmetros específicos do relatório  
			params.putAll(getParams());  

			// obter o caminho real (file system) para o relatório  
			FacesContext facesContext = FacesContext.getCurrentInstance();  
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();  
			String reportUrlReal = request.getRealPath(reportUrl);  
			
			//String anoBase=getAnoBase();			
//			if(anoBase.isEmpty()){
//				this.error = "Não foi possivel gerar o relatorio. Comunique o Administrador do sistema: (Ano da base de dados vazio.)";
//				return null;
//			}

		//	ConnectionJDBC conn = ConnectionJDBC.getInstancia(anoBase);
			
//			if(!conn.isConnected()){
//				this.error = "Não foi possível criar conexão com o servidor";
//				return null;
//			}
			
			// imprimir o relatório para um stream em PDF  
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportUrlReal, params, DS);  
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
			
			
			// armazenar o relatório do DocumentStore do SEAM  
			String reportId = documentStore.newId();  
			DocumentData data = new DocumentData("Report #" + reportId , DocType.PDF, output.toByteArray());
			documentStore.saveData(reportId, data);
		
			// retornar para o caminho padrão do SEAM para exibição de relatórios  
			
			String fileName = "/seam-doc?docId=" + reportId;
			
			HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
			response.setHeader("Content-Disposition", "attachment; filename="+fileName);
			
			return fileName;
			       
		} catch (Exception e) {
			this.error = "Não foi possível gerar o relatório";
			e.printStackTrace();
			return null;  
		}  	
	}
	
	@SuppressWarnings("deprecation")
	public String createPdfReport3(JRDataSource DS,String fileName){			
		try {  
			
			Map<String, Object> params = new HashMap<String, Object>();  
			
			params.putAll(getParams());  

			// obter o caminho real (file system) para o relatório  
			FacesContext facesContext = FacesContext.getCurrentInstance();  
			
			// imprimir o relatório para um stream em PDF  
			JasperPrint jasperPrint = JasperFillManager.fillReport(getClass().getResourceAsStream(getReportPath()), params, DS);  
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
			JRExporter exporter = null;
			String path = "/";  
			try{
				exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path + fileName);  
				exporter.exportReport();
			}catch (RuntimeException e) {
				e.fillInStackTrace();
			}
			
//			"prestadorRelatorio.pdf"
//			// armazenar o relatório do DocumentStore do SEAM  
//			String reportId = documentStore.newId();  
//			DocumentData data = new DocumentData("Report #" + reportId , DocType.PDF, output.toByteArray());
//			documentStore.saveData(reportId, data);
////			String documentUrl = documentStore.preferredUrlForContent(data.getBaseName(), data.getDocType(), reportId);
//		
//			// retornar para o caminho padrão do SEAM para exibição de relatórios  
			
			String pathFinal=path+fileName;

//			fileName=path+"prestadorRelatorio.pdf";
//			System.out.println("filename "+fileName);
	
			return pathFinal;
			       
		} catch (Exception e) {
			this.error = "Não foi possível gerar o relatório";
			e.printStackTrace();
			return null;  
		}  	
	}


	
//	public EntityManager getEntityManager() {
//		try {	
//		String ano=this.anoBase;
//		Boolean baseIsSelected=getAnoBaseIsSelected();
//		if(baseIsSelected==null){
//			return this.entityManager;
//		}
//		if(baseIsSelected){	
//	//		this.entityManager=tbanoHome.getEntityManagerbyAno();
//		} 
//		else{
//			if(ano!=null){
//				if(!ano.isEmpty()){
//					this.entityManager=this.tbanoHome.getEntityManagerbyAno(ano);
//				}
//			}
//		}
//		} catch (Exception e) {
//			System.out.println("ReportAction>getEntityManager: Erro ao selecionar a base de Dados:()");
//			e.printStackTrace();
//			this.error = "Não foi possivel gerar o relatorio)";          
//		}
//		return entityManager;
//	}


	public FacesMessages getFacesMessages() {
		return facesMessages;
	}

	public void setFacesMessages(FacesMessages facesMessages) {
		this.facesMessages = facesMessages;
	}
	
	public String getError() {
		return error;
	}
	
	public String getAnoBase() {		
		Boolean baseIsSelected=getAnoBaseIsSelected();
		try {
		if(baseIsSelected==null){
			this.error = "Não foi possivel gerar o relatorio.)";
			return "";
		}
//		if(baseIsSelected){	
//			Tbano tbano=tbanoHome.getTbano();	
//			if(tbano==null){
//				this.error = "Não foi possivel gerar o relatorio. ";
//				return "";
//			}
//			anoBase=tbano.getNrano();			
//		} 
		if(anoBase==null){
			anoBase="";
		}
		} catch (Exception e) {
			this.error = "Não foi possivel gerar o relatorio. (Ano não selecionado ou preenchido corretamente). ";
			System.out.println("ReportAction>getAnoBase:"+e);
		}
		return anoBase;
	}

	public void setAnoBase(String anoBase) {
		this.anoBase = anoBase;
	}

	public void setError(String error) {
		this.error = error;
	}

	

}