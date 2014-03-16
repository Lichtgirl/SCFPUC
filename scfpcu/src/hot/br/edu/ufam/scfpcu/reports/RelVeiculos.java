package br.edu.ufam.scfpcu.reports;


import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("relVeiculos")
@Scope(ScopeType.SESSION)
public class RelVeiculos extends ReportAction {
	
	
	private Date dtinicio;
	private Date dtfim;

	
	@SuppressWarnings("unchecked")
	public String imprimir() {
	
		
	
		
						
		if (!canCreateReport()){
			  System.out.println("entrei no !can create");
			return null;
		}
		
		String result = null;
		result = super.createPdfReport();				
		if (result == null) {
			super.getFacesMessages().addToControl("ordem", FacesMessage.SEVERITY_ERROR, super.getError());
		}else{
			System.out.println("Sucesso");
		}
		
		//Transferir para quando sair da pagina
		File file = new File("C:/Users/Public/Documents/arquivo.pdf");
		file.deleteOnExit();
		return result;
	}
	
	protected String getReportPath() {
		return "/relatorios/veiculos.jasper";
	}

	@Override
	protected boolean canCreateReport() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	protected Map<String, Object> getParams() throws ParseException {
		

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
				
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("DTINICIO;;;;"+ this.dtinicio);
		String d1 = format.format(this.dtinicio);
		String d2 = format.format(this.dtfim);
		
		this.dtinicio = (Date)format.parse(d1);
		this.dtfim = (Date)format.parse(d2);
		
		
		
		
		map.put("Dt_inicio", this.dtinicio );
		map.put("Dt_fim", this.dtfim );
		
		
		
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
