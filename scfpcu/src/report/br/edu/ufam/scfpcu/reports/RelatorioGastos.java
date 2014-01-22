package br.edu.ufam.scfpcu.reports;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("relatorioGastos")

public class RelatorioGastos extends ReportAction{
	private String idGastosde;
	private String idGastosAte;
	private Date emissao;
	//private String ano;
	
	public String getIdGastosde() {
		return idGastosde;
	}

	public void setIdGastosde(String idGastosde) {
		this.idGastosde = idGastosde;
	}

	public String getIdGastosAte() {
		return idGastosAte;
	}

	public void setIdGastosAte(String idGastosAte) {
		this.idGastosAte = idGastosAte;
	}

	public String imprimir(){
		if(!canCreateReport()){
			return null;
		}
		
		//setAnoBase(getAno());
		
		String result = null;
		result = super.createPdfReport();
		if (result == null){
			System.out.println("Erro");
			super.getFacesMessages().addToControl("ordem",
					FacesMessage.SEVERITY_ERROR, super.getError());					
		}else{
			System.out.println("Sucesso");
		}
		return result;
	}
	
	protected String getReportPath(){
		return "/report/relgastos/relgastos.jasper";
	}
		
	protected boolean canCreateReport(){
		return true;
	}	
	
	protected Map<String, Object> getParams() {
		
		emissao = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (this.idGastosde.isEmpty())
			this.idGastosde="0";
		if (this.idGastosAte.isEmpty())
			this.idGastosAte="99999";	
		
		map.put("IDGASTOSDE", Integer.parseInt(this.idGastosde));
		map.put("IDGASTOSATE", Integer.parseInt(this.idGastosAte));
		map.put("EMISSAO", this.emissao);
		
		return map;
	}
	
	
	protected Boolean getAnoBaseIsSelected() {
		// TODO Auto-generated method stub
		return true;
	}

	
}