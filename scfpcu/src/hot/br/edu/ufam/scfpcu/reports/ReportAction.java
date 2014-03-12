package br.edu.ufam.scfpcu.reports;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuterFactory;

import org.jboss.seam.annotations.In;
import org.jboss.seam.core.Manager;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.pdf.DocumentData;
import org.jboss.seam.pdf.DocumentData.DocType;
import org.jboss.seam.pdf.DocumentStore;

import br.edu.ufam.scfpcu.reports.ConnectionJDBC;
 

public abstract class ReportAction {
	
	@In
	EntityManager entityManager;
	@In
	FacesMessages facesMessages;
	
	
//	@In(value = "org.jboss.seam.pdf.documentStore", create = true)  
	DocumentStore documentStore;
	
	private String error;
	
//	public JRBeanCollectionDataSource getBeanCollectionDataSource(){
//		return null;
//	}
	
	protected abstract String getReportPath();
	protected abstract boolean canCreateReport();
	protected abstract Map<String, Object> getParams();
	
		
	@SuppressWarnings("deprecation")
	public String createPdfReport(){
		System.out.println("entrei no createpdf");
		String reportUrl = getReportPath(); 

		
		
		try {  
			System.out.println("entrei no try");
			Map<String, Object> params = new HashMap<String, Object>();  
				
			System.out.println("vamos a conexao");
			
			ConnectionJDBC conn = ConnectionJDBC.getInstancia();
			
			if(!conn.isConnected()){
				this.error = "Não foi possível criar conexão com o servidor";
				return null;
			}
			
			//params.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, entityManager);
						
			// obter os parâmetros específicos do relatório  
			params.putAll(getParams());  
			//params.put(dtinicio, dtfim);

			// obter o caminho real (file system) para o relatório  
			FacesContext facesContext = FacesContext.getCurrentInstance();  
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();  
			String reportUrlReal = request.getRealPath(reportUrl);  
			
			
			
			
			  System.out.println("Entrando no imprimir");
			// imprimir o relatório para um stream em PDF  
			  System.out.println("Report url real    "+reportUrlReal);
			  System.out.println("params    "+params);
			  System.out.println("conexao    "+conn.getConection());
			 
			  System.out.println("Cheguei no Jasperprint");
			  
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportUrlReal, params, conn.getConection());  //erro nessaa linha
			 System.out.println("passei do Jasperprint");
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, output);
						
			// armazenar o relatório do DocumentStore do SEAM  
			System.out.println("Tentarei o documento linha 89");
			String reportId = documentStore.newId(); 
			DocumentData data = new DocumentData("Report #" + reportId , DocType.PDF, output.toByteArray());
			documentStore.saveData(reportId, data);
		
			// retornar para o caminho padrão do SEAM para exibição de relatórios  
			System.out.println("Vou pro filename");
			
			String fileName = "/seam-doc?docId=" + reportId;
			
			HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
			response.setHeader("Content-Disposition", "attachment; filename="+fileName);
			
			System.out.println("vai pro return");
			
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
