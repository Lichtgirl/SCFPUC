package br.edu.ufam.scfpcu.action;

import br.edu.ufam.scfpcu.model.*;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
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
	private double valor;
	private int qtd;


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
	
	
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
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
	
	public String setarValorTotal(){
		System.out.println("setarValorTotal");
		System.out.println("this.getInstance().getQtd()::"+this.getInstance().getQtd());
		System.out.println("this.getInstance().getValor()::"+this.getInstance().getValor());
		this.valorTotal = this.getInstance().getQtd() * this.getInstance().getValor();
		System.out.println("setarValorTotal::::::::"+this.valorTotal);
		return "";
	}
	
	public String persist(){
		System.out.println("persist");
		String result="falhou";
		try {
			result = super.persist();
		} catch (Exception e) {
			System.out.println("falhou...Exception e:::"+ e);
		}
		return result;
	}
}
