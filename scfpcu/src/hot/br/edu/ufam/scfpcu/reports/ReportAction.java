package br.edu.ufam.scfpcu.reports;

import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;




import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;



import org.jboss.seam.annotations.In;
import org.jboss.seam.core.Manager;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.pdf.DocumentStore;
 

public abstract class ReportAction {
	
	@In 
	private Manager manager;
	
	@In(create = true)
	private EntityManager entityManager;

	
	@In
	FacesMessages facesMessages;
	
	
//	@In(value = "org.jboss.seam.pdf.documentStore", create = true)  
	DocumentStore documentStore = new DocumentStore();
	
	private String error;
	

	
	protected abstract String getReportPath();
	protected abstract boolean canCreateReport();
	protected abstract Map<String, Object> getParams() throws ParseException;
	
		
	@SuppressWarnings("deprecation")
	public String createPdfReport(){
	
		String reportUrl = getReportPath(); 
		
		ConnectionJDBC conn = null;
		
		
		try {  
			
			Map<String, Object> params = new HashMap<String, Object>();  
				
			
			
			conn = ConnectionJDBC.getInstancia();
			
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
			
		//	ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Users/Public/Documents/arquivo.pdf");
			java.awt.Desktop.getDesktop().open(new File("C:/Users/Public/Documents/arquivo.pdf"));
			
//			final JasperViewer jv = new JasperViewer(jasperPrint, false);
//			jv.setVisible(true);
//			jv.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
//			
			
			// armazenar o relatório do DocumentStore do SEAM  
//			System.out.println("Tentarei o documento linha 102");
//			String reportId = documentStore.newId(); 
//			DocumentData data = new DocumentData("Report #" + reportId , DocType.PDF, output.toByteArray());
//			documentStore.saveData(reportId, data);
//		
//			// retornar para o caminho padrão do SEAM para exibição de relatórios  
		
			
			String fileName = "/seam-doc?docId=";// + reportId;
			
//			HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
//			response.setHeader("Content-Disposition", "attachment; filename="+fileName);
			
			
			
			return fileName;
			       
		} catch (Exception e) {
			this.error = "Não foi possível gerar o relatório";
			e.printStackTrace();
			return null;  
		}  	
	}
	
	

	
	
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public FacesMessages getFacesMessages() {
		return facesMessages;
	}

	public void setFacesMessages(FacesMessages facesMessages) {
		this.facesMessages = facesMessages;
	}
}
