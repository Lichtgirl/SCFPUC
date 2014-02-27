package br.edu.ufam.scfpcu.reports;

import java.io.ByteArrayOutputStream;
import java.util.Date;
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
import org.jboss.seam.core.Manager;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.pdf.DocumentData;
import org.jboss.seam.pdf.DocumentData.DocType;
import org.jboss.seam.pdf.DocumentStore;

public abstract class ReportAction {
	
//	@In(value = "org.jboss.seam.pdf.documentStore", create = true)  
	DocumentStore documentStore;
	
	private String error;
	protected abstract String getReportPath();
	protected abstract boolean canCreateReport();
	protected abstract Map<String, Object> getParams();
	
	
	@SuppressWarnings("deprecation")
	public String createPdfReport(){
		    
		String reportUrl = getReportPath(); 

		
		
		try {  
			
			Map<String, Object> params = new HashMap<String, Object>();  
			
				
			// obter os parâmetros específicos do relatório  
			params.putAll(getParams());  
			//params.put(dtinicio, dtfim);

			// obter o caminho real (file system) para o relatório  
			FacesContext facesContext = FacesContext.getCurrentInstance();  
			HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();  
			String reportUrlReal = request.getRealPath(reportUrl);  
			

			
			// imprimir o relatório para um stream em PDF  
			JasperPrint jasperPrint = JasperFillManager.fillReport(reportUrlReal, params);  //erro nessaa linha  
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
	
	public void setError(String error) {
		this.error = error;
	}
}
