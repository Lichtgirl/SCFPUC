package br.edu.ufam.scfpcu.reports;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jboss.seam.annotations.Name;

@Name("relVeiculos")
public class RelVeiculos extends ReportAction {
	
	
	private Date dtinicio;
	private Date dtfim;
	
	public RelVeiculos(){
		
	}
		
	@SuppressWarnings("unchecked")
	public String imprimir() {
	
		
		System.out.println("Entrei no imprimir");
		
		
		
				
		if (!canCreateReport()){
			  System.out.println("entrei no !can create");
			return null;
		}
		
		String result = null;
		result = super.createPdfReport();
		  System.out.println("sai do super.createpdfcom reult:::::"+result);
		if (result == null) {
			System.out.println("Erro");
			
		}else{
			System.out.println("Sucesso");
		}
		return result;
	}
	
	protected String getReportPath() {
		return "/veiculo/veiculos.jasper";
	}

	@Override
	protected boolean canCreateReport() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected Map<String, Object> getParams() {
		System.out.println("entrei no map"); 
		Map<String, Object> map = new HashMap<String, Object>();
		
				
		//map.put("DTINICIO", dtinicio);
		//map.put("DTFIM", dtfim);
		
		
		return map;
	}

	public Date getDtinicio() {
		return dtinicio;
	}

	public void setDtinicio(Date dtinicio) {
		this.dtinicio = dtinicio;
	}

	public Date getDtfim() {
		return dtfim;
	}

	public void setDtfim(Date dtfim) {
		this.dtfim = dtfim;
	}


}	
