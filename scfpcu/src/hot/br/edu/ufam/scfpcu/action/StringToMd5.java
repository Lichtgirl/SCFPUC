package br.edu.ufam.scfpcu.action;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class StringToMd5 {

	public static String md5(String senha) {
		String sen = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
		sen = hash.toString(16);
		return sen.substring(0, 15);
	}
	
	public static String string(){
		
		String senha = UUID.randomUUID().toString();
		System.out.println("Senha Random::"+senha.substring(0,6));
		return senha.substring(0,6);
	}
}