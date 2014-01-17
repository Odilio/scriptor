package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Biblioteca;
import models.Capitulo;
import models.CapituloSolo;
import models.Local;
import models.Local_capitulo;
import models.Musica;
import models.Nota;
import models.Personagem;
import models.Personagem_capitulo;
import models.Posts;
import models.Projeto;
import models.Tipo;
import models.Usuario;
import play.cache.Cache;

public class Estante extends Application {

	public static void index(Projeto projeto) {
		List<Biblioteca> lista = Biblioteca.find("byUsu_codigo", renderArgs.get("user", Usuario.class).usu_codigo ).fetch();
		
		render(lista);
	}

	}
