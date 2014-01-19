package br.edu.ufam.scfpcu.action;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;

import br.edu.ufam.scfpcu.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.security.Identity;

import br.edu.ufam.scfpcu.action.CargoHome;
import br.edu.ufam.scfpcu.action.StringToMd5;

@Name("usuarioHome")
public class UsuarioHome extends EntityHome<Usuario> {

	@In(create = true)
	CargoHome cargoHome;
	
	@In(create = true)
	Identity identity;
	
	private boolean editaSenha;

//	public boolean isEditaSenha() {
//		System.out.println("isEditaSenha???");
//	Usuario usuario = getUsuarioByLogin();
//	if(usuario != null){
//		if(this.getInstance() == usuario)
//			editaSenha = true;
//		else
//			editaSenha = false;
//	}else
//		editaSenha = false;
//	
//	return editaSenha;
//}

	public void setEditaSenha(boolean editaSenha) {
		this.editaSenha = editaSenha;
	}

	public void setUsuarioIdServidor(Integer id) {
		setId(id);
	}

	public Integer getUsuarioIdServidor() {
		return (Integer) getId();
	}

	@Override
	protected Usuario createInstance() {
		Usuario usuario = new Usuario();
		return usuario;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Cargo cargo = cargoHome.getDefinedInstance();
		if (cargo != null) {
			getInstance().setCargo(cargo);
		}
	}

	public boolean isWired() {
		if (getInstance().getCargo() == null)
			return false;
		return true;
	}

	public Usuario getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}
	
//	private Usuario getUsuarioByLogin(){
//		System.out.println("getUsuarioByLogin");
//		System.out.println("identity.getUsername()::"+ identity.getUsername());
//		
//		Usuario usuario = null;
//		if(identity.getUsername() != null){
//			usuario = (Usuario) super.getEntityManager().createQuery("from Usuario usuario where usuario.login = '"+identity.getUsername()+"'" ).getSingleResult();
//			System.out.println("usuario::::"+usuario.getIdServidor());
//		}
//		
//		return usuario;
//	}
	
	
	@Override
	public String persist(){
		System.out.println("persistindo/.....");
		System.out.println("this.getInstance().getLogin()::::"+this.getInstance().getLogin());
		List<Usuario>  usuario = (List<Usuario>) super.getEntityManager().createQuery("from Usuario where login = '"+this.getInstance().getLogin()+"'" ).getResultList();
		System.out.println("usuario.size()..."+usuario.size());
		if(!usuario.isEmpty()){
			super.getFacesMessages().addToControl("txLoginUsuario",
					FacesMessage.SEVERITY_ERROR, "Já existe um usuário com o mesmo login, por favor escolha outro login");
			return "";
		}
		
		String result = "";
		this.getInstance().setSenha(StringToMd5.md5(this.getInstance().getSenha()));
		
		try {
			result =  super.persist();
		} catch (Exception e) {
			super.getFacesMessages().add("Não foi possível fazer o cadastro.");
		}
		
		return result;
	}
	
	@Override
	public String update(){
		
		String result = "";
		this.getInstance().setSenha(StringToMd5.md5(this.getInstance().getSenha()));
		try {
			result = super.update();
		} catch (Exception e) {
			super.getFacesMessages().add("Não foi possível atualizar registro.");
		}
		return result;
	}
}
