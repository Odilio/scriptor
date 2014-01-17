package controllers;

import play.*;
import play.cache.Cache;
import play.data.validation.Valid;
import play.libs.Crypto;
import play.libs.Images;
import play.libs.OAuth2;
import play.mvc.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import models.*;
public class Application extends Controller {
	
	
    public static void index2() {
    	  List<Usuario> contacts = Cache.get("contacts", List.class);
    	  if(contacts == null) {
    		  contacts = Usuario.all().fetch();
              Cache.set("contacts", contacts, "30mn");
          }
        render(contacts);
    }
        
   
    public static void login(String username, String password) {
    	Crypto a = new Crypto();
        String senha = a.encryptAES(password);
    	Usuario user = Usuario.find("byUsu_nomeAndUsu_senha", username, senha).first();
        if(user != null) {
            session.put("user", user.usu_nome);
           Timeline.teste(user);         
        }
        // Oops
        flash.put("username", username);
        flash.error("Login failed");
        index2();
    }
   

    @Before
    static void addUser() {
    	Usuario user = connected();
        if(user != null) {
            renderArgs.put("user", user);
        }
    }
    
   
    
    static Usuario connected() {
        if(renderArgs.get("user") != null) {
            return renderArgs.get("user", Usuario.class);
        }
        String username = session.get("user");
        if(username != null) {
            return Usuario.find("byUsu_nome", username).first();
        } 
        return null;
    }
    
    
    public static void logout() {
        session.clear();
        index2();
    }
    
    public static void registrar() {
       
        render();
    }
    
    public static void saveUser(@Valid Usuario user, String verifyPassword, String verifyEmail) {
       //validation.required(verifyPassword).message("Campo Obrigatorio");
       //validation.equals(verifyPassword, user.usu_senha).message("Seu password não confere");
       // validation.required(verifyEmail).message("Campo Obrigatorio");
       // validation.equals(verifyEmail, user.email).message("Seu email não confere");
        if(validation.hasErrors()) {
            render("@registrar", user, verifyPassword);
        }
      /*  File f = user.usu_ima.getFile();;
       String s = user.usu_ima.type();
        File newFile = new File(user.usu_nome+user.usu_senha); // create random unique filename here
        Images.resize(f, newFile, 500, -1);
        FileInputStream a = null;
		try {
			a = new FileInputStream(newFile);
		
        user.usu_ima.set(a, s); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        Crypto a = new Crypto();
        String senha = a.encryptAES(user.usu_senha);
        user.usu_senha = senha;
        user.save();
        Pessoa pessoa = new Pessoa();
        pessoa.pes_nome = user.usu_nome;
        
        session.put("user", user.usu_nome);
        index2();
    }
    
    public static void userPhoto(int id) {
    	   final Usuario user = Usuario.findById(id);
    	   notFoundIfNull(user);
    	   response.setContentTypeIfNotSet(user.usu_ima.type());
    	   renderBinary(user.usu_ima.get());
    	}
    
    
}