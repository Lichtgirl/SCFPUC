package br.edu.ufam.scfpcu.reports;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jboss.seam.annotations.Name;

@Name("relVeiculos")
public class RelVeiculos extends ReportAction {
	
	
	private Date dtinicio;
	private Date dtfim;
	private Calendar c1;
	private Calendar c2;
	
	public RelVeiculos(){
		
		c1 = Calendar.getInstance();
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		
		c2 = Calendar.getInstance();
		c2.set(Calendar.HOUR_OF_DAY, 23);
		c2.set(Calendar.MINUTE, 59);
		c2.set(Calendar.SECOND, 59);
		
		this.dtinicio = c1.getTime();
		this.dtfim = c2.getTime();
		
	}
		
	@SuppressWarnings("unchecked")
	public String imprimir(Date datinicio) {
	
		
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
		return "relatorios/veiculos.jasper";
	}

	@Override
	protected boolean canCreateReport() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected Map<String, Object> getParams() {
		System.out.println("entrei no map"); 
		
		String filtro;
		String di = "2013-12-01"; 
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
				
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("DTINICIO"+ di);
//		System.out.println("DTFIM"+ (this.dtfim).toString());
		
		
		map.put("DTINICIO", di);
		map.put("DTFIM", format.format(this.dtfim));
		
		
		
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
