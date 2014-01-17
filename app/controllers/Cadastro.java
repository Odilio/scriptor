package controllers;

import java.util.List;

import models.Estabelecimento;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;

public class Cadastro extends Application {

	public static void index() {
		render();
	}
	public static void cadastroPosicao() {
		 List<Estabelecimento> contacts = Cache.get("contacts", List.class);
	   	  if(contacts == null) {
	   		  contacts = Estabelecimento.all().fetch();
	             Cache.set("contacts", contacts, "30mn");
	         }
	       render(contacts);
		
	}
	public static void salvar( Estabelecimento estabelecimento) {
        /*validation.required(name);
        validation.required(rarazaoSocial);*/
        if(validation.hasErrors()) {
            params.flash(); // add http parameters to the flash scope
            validation.keep(); // keep the errors for the next request
            index2();
        }
        
        estabelecimento.save();
       
        cadastroPosicao();
     }
}
