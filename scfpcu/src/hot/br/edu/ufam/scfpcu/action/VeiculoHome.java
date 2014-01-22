package br.edu.ufam.scfpcu.action;

import br.edu.ufam.scfpcu.model.*;
import java.util.ArrayList;
import java.util.Date;
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
	private Integer idModelo;
	private Estados estado = new Estados();
	private Integer idCidadePanterior;
	private Integer idCidadePatual;
	private List<SelectItem> listModelos = new ArrayList<SelectItem>();
	private List<SelectItem> listCidadeAnterior = new ArrayList<SelectItem>(); 
	private List<SelectItem> listCidadeAtual = new ArrayList<SelectItem>();

	public List<SelectItem> getListModelos() {
		return listModelos;
	}


	public void setListModelos(List<SelectItem> listModelos) {
		this.listModelos = listModelos;
	}


	public void setVeiculoIdVeiculo(Integer id) {
		setId(id);
	}
	
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Integer getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(Integer idModelo) {
		this.idModelo = idModelo;
	}

	public Estados getEstado() {
		return estado;
	}
	
	public void setEstado(Estados estado) {
		this.estado = estado;
	}

	public Integer getIdCidadePanterior() {
		return idCidadePanterior;
	}


	public void setIdCidadePanterior(Integer idCidadePanterior) {
		this.idCidadePanterior = idCidadePanterior;
	}


	public Integer getIdCidadePatual() {
		return idCidadePatual;
	}


	public void setIdCidadePatual(Integer idCidadePatual) {
		this.idCidadePatual = idCidadePatual;
	}


	public List<SelectItem> getListCidadeAnterior() {
		return listCidadeAnterior;
	}


	public void setListCidadeAnterior(List<SelectItem> listCidadeAnterior) {
		this.listCidadeAnterior = listCidadeAnterior;
	}


	public List<SelectItem> getListCidadeAtual() {
		return listCidadeAtual;
	}


	public void setListCidadeAtual(List<SelectItem> listCidadeAtual) {
		this.listCidadeAtual = listCidadeAtual;
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
			lModelosNova.add(new SelectItem(modelo.getIdModelo(), modelo.getModelo()));
		}
		return lModelosNova;
	}
	
	public List<SelectItem> getEstados(){
		System.out.println("get Estados");
		List<Estados> listEstados = (List<Estados>)super.getEntityManager().createQuery("from Estados").getResultList();
		
		List<SelectItem> listaNovaEstados = new ArrayList<SelectItem>(listEstados.size());
		for (Estados estado : listEstados) {
			listaNovaEstados.add(new SelectItem(estado.getId(), estado.getNome()));
		}
		
		return listaNovaEstados;
	}
	
	public String actionCarregaCidadesByEstado(){
		this.listCidadeAnterior = getCarregaCidadesByEstado(this.estado.getId());

		return "getCarregaCidadesByEstado";
	}

	public List<SelectItem> getCarregaCidadesByEstado(Integer idEstado){

		List<Cidades> lCidades = (List<Cidades>)super.getEntityManager().createQuery("from Cidades cidades where cidades.estados.id = "+ idEstado).getResultList();
		List<SelectItem> lCidadesNova = new ArrayList<SelectItem>(lCidades.size());

		for(Cidades cidades: lCidades){
			lCidadesNova.add(new SelectItem(cidades.getIdCidade(), cidades.getNome()));
		}
		return lCidadesNova;
	}
	
	public String actionCarregaCidadesAtualByEstado(){
		this.listCidadeAtual = getCarregaCidadesAtualByEstado(this.estado.getId());

		return "getCarregaCidadesByEstado";
	}

	public List<SelectItem> getCarregaCidadesAtualByEstado(Integer idEstado){

		List<Cidades> lCidades = (List<Cidades>)super.getEntityManager().createQuery("from Cidades cidades where cidades.estados.id = "+ idEstado).getResultList();
		List<SelectItem> lCidadesNova = new ArrayList<SelectItem>(lCidades.size());

		for(Cidades cidades: lCidades){
			lCidadesNova.add(new SelectItem(cidades.getIdCidade(), cidades.getNome()));
		}
		return lCidadesNova;
	}
	
	
	public String persist(){
		VeiculoList veiculoList = new VeiculoList();
		List<Veiculo> lVeiculos = veiculoList.getResultList();
		
		for (Veiculo veiculo : lVeiculos) {
			if(veiculo.getPlacaAtual().equals(this.getInstance().getPlacaAtual())){
				super.getFacesMessages().add("Placa atual já cadastrada");
				return "";
			}else if (veiculo.getChassi().equals(this.getInstance().getChassi())) {
				super.getFacesMessages().add("Chassi já cadastrado");
				return "";
			}else if ((veiculo.getCodRenavam() == this.getInstance().getCodRenavam())) {
				super.getFacesMessages().add("Código Renavam já cadastrado");
				return "";
			}else if (veiculo.getNumPatrimonio().equals(this.getInstance().getNumPatrimonio())) {
				super.getFacesMessages().add("Número de Patrimônio já cadastrado");
				return "";
			}
		}

		Modelo modelo = super.getEntityManager().find(Modelo.class, this.idModelo);
		Cidades cidadeAnterior = super.getEntityManager().find(Cidades.class, this.idCidadePanterior);
		Cidades cidadeAtual = super.getEntityManager().find(Cidades.class, this.idCidadePatual);
		this.getInstance().setModelo(modelo);
		this.getInstance().setCidadesByIdCidadePanterior(cidadeAnterior);
		this.getInstance().setCidadesByIdCidadePatual(cidadeAtual);
		this.getInstance().setDataCadastro(new Date());

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
