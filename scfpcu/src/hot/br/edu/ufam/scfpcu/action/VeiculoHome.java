package br.edu.ufam.scfpcu.action;

import br.edu.ufam.scfpcu.model.*;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("veiculoHome")
public class VeiculoHome extends EntityHome<Veiculo> {

	@In(create = true)
	CidadesHome cidadesHome;
	@In(create = true)
	CorHome corHome;
	@In(create = true)
	EntidadeHome entidadeHome;
	@In(create = true)
	EspecieHome especieHome;
	@In(create = true)
	GrupoHome grupoHome;
	@In(create = true)
	ModeloHome modeloHome;
	@In(create = true)
	TipoCombustivelHome tipoCombustivelHome;
	
	private Marca marca = null;
	private Estados estado = new Estados();
	private List<Modelo> listModelos = new ArrayList<Modelo>();
	 private List<SelectItem> listCidades = new ArrayList<SelectItem>(); 

	public void setVeiculoIdVeiculo(Integer id) {
		setId(id);
	}
	
	public List<SelectItem> getListCidades() {
		return listCidades;
	}

	public void setListCidades(List<SelectItem> listCidades) {
		this.listCidades = listCidades;
	}

	public List<Modelo> getListModelos() {
		return listModelos;
	}

	public void setListModelos(List<Modelo> listModelos) {
		this.listModelos = listModelos;
	}
	
	public String setListModelos() {
		
		if(this.marca != null){
			this.listModelos = super.getEntityManager().createQuery("from Modelo modelo where modelo.marca.idMarca = "+ this.marca.getIdMarca())
					.getResultList();
		}
		
		return "";
	}


	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Estados getEstado() {
		return estado;
	}
	
	public void setEstado(Estados estado) {
		this.estado = estado;
	}

	public Integer getVeiculoIdVeiculo() {
		return (Integer) getId();
	}

	@Override
	protected Veiculo createInstance() {
		Veiculo veiculo = new Veiculo();
		return veiculo;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Cidades cidadesByIdCidadePanterior = cidadesHome.getDefinedInstance();
		if (cidadesByIdCidadePanterior != null) {
			getInstance().setCidadesByIdCidadePanterior(
					cidadesByIdCidadePanterior);
		}
		Cidades cidadesByIdCidadePatual = cidadesHome.getDefinedInstance();
		if (cidadesByIdCidadePatual != null) {
			getInstance().setCidadesByIdCidadePatual(cidadesByIdCidadePatual);
		}
		Cor cor = corHome.getDefinedInstance();
		if (cor != null) {
			getInstance().setCor(cor);
		}
		Entidade entidade = entidadeHome.getDefinedInstance();
		if (entidade != null) {
			getInstance().setEntidade(entidade);
		}
		Especie especie = especieHome.getDefinedInstance();
		if (especie != null) {
			getInstance().setEspecie(especie);
		}
		Grupo grupo = grupoHome.getDefinedInstance();
		if (grupo != null) {
			getInstance().setGrupo(grupo);
		}
		Modelo modelo = modeloHome.getDefinedInstance();
		if (modelo != null) {
			getInstance().setModelo(modelo);
		}
		TipoCombustivel tipoCombustivel = tipoCombustivelHome
				.getDefinedInstance();
		if (tipoCombustivel != null) {
			getInstance().setTipoCombustivel(tipoCombustivel);
		}
	}

	public boolean isWired() {
		if (getInstance().getCidadesByIdCidadePatual() == null)
			return false;
		if (getInstance().getCor() == null)
			return false;
		if (getInstance().getEntidade() == null)
			return false;
		if (getInstance().getEspecie() == null)
			return false;
		if (getInstance().getGrupo() == null)
			return false;
		if (getInstance().getModelo() == null)
			return false;
		if (getInstance().getTipoCombustivel() == null)
			return false;
		return true;
	}

	public Veiculo getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Gastos> getGastoses() {
		return getInstance() == null ? null : new ArrayList<Gastos>(
				getInstance().getGastoses());
	}
	
	public List<SelectItem> getEstados(){
		System.out.println("get estados");
		List<Estados> listEstados = (List<Estados>)super.getEntityManager().createQuery("from Estados").getResultList();
		
		List<SelectItem> listaNovaEstados = new ArrayList<SelectItem>(listEstados.size());
		
		for (Estados estados : listEstados) {
			listaNovaEstados.add(new SelectItem(estados.getId(),estados.getNome()+ " - " + estados.getUf()));
		}
		
		System.out.println("estados:::"+listaNovaEstados);
		return listaNovaEstados;
	}
	
	 public String actionCarregaCidadesByEstado(){
	        this.listCidades = getCarregaCidadesByEstado(this.estado.getId());
	        
	        return "getSubcategoriasByCategoriaSucesso";
	    }
	 
    public List<SelectItem> getCarregaCidadesByEstado(Integer idEstado){

        List<Cidades> lCidades = (List<Cidades>)super.getEntityManager().createQuery("from Cidades cidade where cidade.estados.id = "+ idEstado).getResultList();
        List<SelectItem> lCidadesNova = new ArrayList<SelectItem>(lCidades.size());
        
        
        for(Cidades cidade : lCidades){
        	lCidadesNova.add(new SelectItem(cidade.getNome()));
        }
        
        System.out.println("lCidadesNova >>> " + lCidadesNova);

        
        return lCidadesNova;
    }
}
