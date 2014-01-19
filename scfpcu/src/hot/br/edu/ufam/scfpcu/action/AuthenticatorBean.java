package br.edu.ufam.scfpcu.action;


import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;


import br.edu.ufam.scfpcu.model.Usuario;

@Stateless
@Name("authenticator")
public class AuthenticatorBean implements Authenticator {
	private static final String LOGIN_INVALIDO = "Usuário ou senha inválido";

	@Logger
	private Log log;

	@In
	Identity identity;
	@In
	Credentials credentials;
	@In
	EntityManager entityManager;
	@In
	FacesMessages facesMessages;
	
	Usuario usuario;
	
	public boolean authenticate() {
		HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext
		.getCurrentInstance().getExternalContext().getRequest();
		String ip = httpServletRequest.getRemoteAddr();
		
		boolean authenticated = false;
		try{

			authenticated = realizarLogin();
			
			if(authenticated){
				log.info("authenticated #0 (#1)", identity.getUsername(), ip);
				Identity.instance().addRole(usuario.getCargo().getCargo());
			}
		}catch(Exception exception){
			log.info("fail authenticating #0 (#1)", identity.getUsername(), ip);
		}
		return authenticated;
	}
	
	private boolean realizarLogin() {
		try
		{
			usuario = (Usuario) entityManager
					.createQuery(
							"from Usuario usuario where usuario.login = :username and usuario.senha = :password ")
					.setParameter("username", Identity.instance().getUsername())
					.setParameter("password", StringToMd5.md5(Identity.instance().getPassword()))
					.getSingleResult();
			return true;
		}
		catch (RuntimeException ex)
		{
			facesMessages.addToControl("username", LOGIN_INVALIDO);
			System.out.println("Nao entrar");
			return false;
		}
		
	}
}
