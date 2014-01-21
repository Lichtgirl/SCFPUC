package br.edu.ufam.scfpcu.action;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.mail.EmailException;  
import org.apache.commons.mail.SimpleEmail;  
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;

import com.sun.xml.internal.stream.Entity;

import br.edu.ufam.scfpcu.model.Usuario;
 
@Name("recuperarSenha")
public class recuperarSenha extends Usuario{  
	@In
	EntityManager entityManager;
	@In
	FacesMessages facesMessages;
	
	UsuarioHome usuarioHome = new UsuarioHome();
	
	
	private String mail;
  
    public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void enviarEmail() {  
        SimpleEmail email = new SimpleEmail();  
        List<Usuario> lUsuario = (List<Usuario>)entityManager.createQuery("from Usuario usuario where usuario.email = '"+this.mail+"'").getResultList();
        if(!lUsuario.isEmpty()){
        	String senha = StringToMd5.string();
        	Usuario usuario = lUsuario.get(0);
        	this.usuarioHome.setInstance(usuario);
        	this.usuarioHome.getInstance().setSenha(senha);
        	
        	String result="";
        	try {
        		result = this.usuarioHome.update();
        		
			} catch (Exception e) {
				// TODO: handle exception
			}
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
	      
	            } catch (EmailException e) {  
	      
	                System.out.println(e.getMessage());  
	      
	            }
			} else
				facesMessages.add("Por favor tente novamente.");
        	  
        }else
        	facesMessages.add("O e-mail informado não está cadastrado");
        
    }  
  
}   