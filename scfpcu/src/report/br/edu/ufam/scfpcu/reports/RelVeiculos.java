package br.edu.ufam.scfpcu.reports;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name ("relVeiculos")
@Scope(ScopeType.SESSION)
public class RelVeiculos extends ReportAction {
	
	private String dtinicio;
	private String dtfim;
	
	
	@SuppressWarnings("unchecked")
	public String imprimir(String dtinicio, String dtfim) {
		if (!canCreateReport()){			
			return null;
		}
		System.out.println("Entrei aqui");
		String result = null;
		result = super.createPdfReport(dtinicio, dtfim);
		if (result == null) {
			System.out.println("Erro");
			
		}else{
			System.out.println("Sucesso");
		}
		return result;
	}
	
	protected String getReportPath() {
		return "/report/veiculos.jasper";
	}

	@Override
	protected boolean canCreateReport() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected Map<String, Object> getParams() {
		 
		Map<String, Object> map = new HashMap<String, Object>();
		
				
		map.put("DTINICIO", Integer.parseInt(this.dtinicio));
		map.put("DTFIM", Integer.parseInt(this.dtfim));
		
		
		return map;
	}

	public String getDtinicio() {
		return dtinicio;
	}

	public void setDtinicio(String dtinicio) {
		this.dtinicio = dtinicio;
	}

	public String getDtfim() {
		return dtfim;
	}

	public void setDtfim(String dtfim) {
		this.dtfim = dtfim;
	}

}	
