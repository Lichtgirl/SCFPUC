package br.edu.ufam.scfpcu.action;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.mail.EmailException;  
import org.apache.commons.mail.SimpleEmail;  
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityHome;

import br.edu.ufam.scfpcu.model.Usuario;
 
@Name("recuperarSenha")
public class recuperarSenha extends EntityHome<Usuario> {  
	@In
	EntityManager entityManager;
	@In
	FacesMessages facesMessages;
	
	private boolean sucesso=false;
	
	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

	private String mail;
  
    public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String enviarEmail() {  
        SimpleEmail email = new SimpleEmail();  
        List<Usuario> lUsuario = null;
        String result="";
        try {
        	lUsuario = (List<Usuario>)entityManager.createQuery("from Usuario usuario where usuario.email = '"+this.mail+"'").getResultList();
		} catch (Exception e) {
			facesMessages.add("Ocorreu um erro, por favor tente novamente.");
			return result;
		}
        
         
		if(!lUsuario.isEmpty()){
			String senha = StringToMd5.string();
			Usuario usuario = lUsuario.get(0);
			super.setInstance(usuario);
			super.getInstance().setSenha(senha);

			result = update();
			if (result == "updated") {
				try {  
					email.setDebug(true);  
					email.setHostName("smtp.gmail.com");  
					email.setAuthentication("suporte.scfpcu@gmail.com", "scfpcu@123");  
					email.setSSL(true);  
					email.addTo(this.mail); //pode ser qualquer um email  
					email.setFrom("suporte.scfpcu@gmail.com"); //aqui necessita ser o email que voce fara a autenticacao  
					email.setSubject("Rucuperação de Senha");  
					email.setMsg("Olá " + usuario.getNome()+ ". Você solicitou a recuperação de senha para acesso "+  
							"a sua conta do Sistema de Controle de Frotas da Prefeitura do Campus Universitário, abaixo seguem seu Login e Senha. \n"  
							+ "Login:" + usuario.getLogin() + "\n" + "Senha:"  
							+ senha);  
					email.send();  
					this.sucesso = true;
					facesMessages.add("Uma nova senha foi enviada para seu e-mail");
					return result;
				} catch (EmailException e) {  
					facesMessages.add("Ocorreu um erro, por favor tente novamente.");
					return "";  
				}
			} else
				facesMessages.add("Ocorreu um erro, por favor tente novamente.");
			return result;

		}else{
        	facesMessages.add("O e-mail informado não está cadastrado");
         	return result;
        }
    }  
	
	@Override
	public String update(){
		String result = "";
		this.getInstance().setSenha(StringToMd5.md5(this.getInstance().getSenha()));
		try {
			result = super.update();
		} catch (Exception e) {
		}
		return result;
	}
	
	@Override
	public void updatedMessage(){
		addFacesMessage("");
	}
  
}   