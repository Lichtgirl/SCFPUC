package br.edu.ufam.scfpcu.action;

import br.edu.ufam.scfpcu.model.*;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("gastosHome")
public class GastosHome extends EntityHome<Gastos> {

	@In(create = true)
	TipoCombustivelHome tipoCombustivelHome;
	@In(create = true)
	TipoServicoHome tipoServicoHome;
	@In(create = true)
	VeiculoHome veiculoHome;
	
	private double valorTotal;
	private Short tipoGasto = null;


	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Short getTipoGasto() {
		return tipoGasto;
	}

	public void setTipoGasto(Short tipoGasto) {
		this.tipoGasto = tipoGasto;
	}

	public void setGastosIdGasto(Integer id) {
		setId(id);
	}

	public Integer getGastosIdGasto() {
		return (Integer) getId();
	}

	@Override
	protected Gastos createInstance() {
		Gastos gastos = new Gastos();
		return gastos;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		TipoCombustivel tipoCombustivel = tipoCombustivelHome
				.getDefinedInstance();
		if (tipoCombustivel != null) {
			getInstance().setTipoCombustivel(tipoCombustivel);
		}
		TipoServico tipoServico = tipoServicoHome.getDefinedInstance();
		if (tipoServico != null) {
			getInstance().setTipoServico(tipoServico);
		}
		Veiculo veiculo = veiculoHome.getDefinedInstance();
		if (veiculo != null) {
			getInstance().setVeiculo(veiculo);
		}
	}

	public boolean isWired() {
		if (getInstance().getVeiculo() == null)
			return false;
		return true;
	}

	public Gastos getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
	public String setValorTotal(){
		this.valorTotal = this.getInstance().getQtd() * this.getInstance().getValor();
		return "";
	}
}
