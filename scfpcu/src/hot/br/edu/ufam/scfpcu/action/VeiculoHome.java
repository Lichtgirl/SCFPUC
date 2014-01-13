package br.edu.ufam.scfpcu.action;

import br.edu.ufam.scfpcu.model.*;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.Query;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.security.Identity;

@Name("veiculoHome")
public class VeiculoHome extends EntityHome<Veiculo> {
	
	@In(create = true)
	Identity identity;

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
	@In(create = true)
	UsuarioHome usuarioHome;
	
	private Marca marca = new Marca();
	private Estados estado = new Estados();
	private List<SelectItem> listModelos = new ArrayList<SelectItem>();
	private List<SelectItem> listCidades = new ArrayList<SelectItem>(); 

	public void setVeiculoIdVeiculo(Integer id) {
		setId(id);
	}
	
	public List<SelectItem> getListModelos() {
		return listModelos;
	}

	public void setListModelos(List<SelectItem> listModelos) {
		this.listModelos = listModelos;
	}

	public List<SelectItem> getListCidades() {
		return listCidades;
	}

	public void setListCidades(List<SelectItem> listCidades) {
		this.listCidades = listCidades;
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
		Usuario usuario = usuarioHome.getDefinedInstance();
		if (usuario != null) {
			getInstance().setUsuario(usuario);
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
	
	public List<SelectItem> getMarcas(){
		System.out.println("get marca");
		List<Marca> listMarcas = (List<Marca>)super.getEntityManager().createQuery("from Marca").getResultList();
		
		List<SelectItem> listaNovaMarca = new ArrayList<SelectItem>(listMarcas.size());
		
		for (Marca marca : listMarcas) {
			listaNovaMarca.add(new SelectItem(marca.getIdMarca(),marca.getMarca()));
		}
		return listaNovaMarca;
	}
	
	public String actionCarregaModeloByMarca(){
		this.listModelos = getCarregaModeloByMarca(this.marca.getIdMarca());

		return "getCarregaModeloByMarcaSucesso";
	}

	public List<SelectItem> getCarregaModeloByMarca(Integer idMarca){

		List<Modelo> lModelos = (List<Modelo>)super.getEntityManager().createQuery("from Modelo modelo where modelo.marca.idMarca = "+ idMarca).getResultList();
		List<SelectItem> lModelosNova = new ArrayList<SelectItem>(lModelos.size());

		for(Modelo modelo: lModelos){
			lModelosNova.add(new SelectItem(modelo.getModelo()));
		}
		return lModelosNova;
	}
	
	public List<SelectItem> getEstados(){
		System.out.println("get estados");
		List<Estados> listEstados = (List<Estados>)super.getEntityManager().createQuery("from Estados").getResultList();
		
		List<SelectItem> listaNovaEstados = new ArrayList<SelectItem>(listEstados.size());
		
		for (Estados estados : listEstados) {
			listaNovaEstados.add(new SelectItem(estados.getId(),estados.getUf()));
		}
		
		System.out.println("estados:::"+listaNovaEstados);
		return listaNovaEstados;
	}
	
	 public String actionCarregaCidadesByEstado(){
	        this.listCidades = getCarregaCidadesByEstado(this.estado.getId());
	        
	        return "getCarregaCidadesByEstadoSucesso";
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
    
    public String persist(){
    	 Usuario user = encontrarUsuarioByLogin( identity.getUsername() );
  	   if (user != null){
  		   this.getInstance().setUsuario(user);
  	   }
  	   
  	 return (super.persist());
    }
    
    public Usuario encontrarUsuarioByLogin(String login) {
		Usuario usuario = null;
		Query query = super.getEntityManager().createQuery(
				"from Usuario usuario " +
					"where usuario.login = '"
						+ login + "'");

		Object Usu = null;
		try {
			Usu = query.getSingleResult();
			usuario = (Usuario) Usu;
		} catch (Exception e) {
		}
		return usuario;
	}
}
