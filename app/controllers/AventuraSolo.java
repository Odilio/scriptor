package controllers;

import java.util.ArrayList;
import java.util.List;

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

public class AventuraSolo extends Projetos {

	public static void projeto(Projeto projeto) {
		List<Projeto> projetos = Projeto.find("byPro_cod_usuario",
				renderArgs.get("user", Usuario.class).usu_codigo).fetch();
		int qtdProjetos = projetos.size();
		List<CapituloSolo> capitulos = CapituloSolo.find("byPro_codigo",
				projeto.pro_codigo).fetch();

		Cache.set("CapituloSolo", capitulos, "40mn");
		List<Personagem> personagens = Personagem.find("byPro_codigo",
				projeto.pro_codigo).fetch();
		Cache.set("personagens", personagens, "40mn");
		List<Nota> notas = Nota.find("byPro_codigo", projeto.pro_codigo)
				.fetch();
		Cache.set("notas", notas, "40mn");
		List<Local> locais = Local.find("byPro_codigo", projeto.pro_codigo)
				.fetch();
		Cache.set("locais", locais, "40mn");

		render(projetos, qtdAmigosL, qtdProjetos, projeto, capitulos,
				personagens, notas, locais);
	}

	public static void capitulo(CapituloSolo capitulo) {
		List<Tipo> tipos = new ArrayList<>();

		List<Projeto> projetos = Projeto.find("byPro_cod_usuario",
				renderArgs.get("user", Usuario.class).usu_codigo).fetch();
		int qtdProjetos = projetos.size();
		List<CapituloSolo> capitulos = Cache.get("CapituloSolo", List.class);
		for (CapituloSolo capitulo2 : capitulos) {
			tipos.add(new Tipo(capitulo2.cap_indice, capitulo2.cap_titulo));
		}

		List<Personagem> personagens = Cache.get("personagens", List.class);
		List<Nota> notas = Cache.get("notas", List.class);
		List<Local> locais = Cache.get("locais", List.class);
		if (capitulo == null) {
			int pro_codigo = Integer.parseInt(params.get("projeto.pro_codigo"));
			Projeto projeto = Projeto.find("byPro_codigo", pro_codigo).first();
			render(projetos, qtdAmigosL, qtdProjetos, personagens, projeto);
		}
		Projeto projeto = Projeto.find("byPro_codigo", capitulo.pro_codigo)
				.first();

		List<Personagem_capitulo> pers_caps = Personagem_capitulo.find(
				"byCap_codigo", capitulo.cap_codigo).fetch();
		List<Local_capitulo> local_capitulo = Local_capitulo.find(
				"byCapitulo_cap_codigo", capitulo.cap_codigo).fetch();
		List<Musica> musicas = new ArrayList<>();
		musicas.add(new Musica(1, "Sem Trilha"));
		musicas.add(new Musica(2, "Ativar Leitura do texto automatica"));
		musicas.add(new Musica(3,"18-machinae_supremacy-lord_krutors_dominion.mp3"));
		musicas.add(new Musica(4, "Moonlight Sonata.mp3"));
		musicas.add(new Musica(5, "209563__dneproman__voice-of-end-1.mp3"));
		musicas.add(new Musica(6, "18765__reinsamba__evening-in-the-forest.mp3"));
		render(projetos, capitulos, qtdAmigosL, qtdProjetos, capitulo, projeto,
				personagens, notas, locais, pers_caps, local_capitulo, tipos,
				musicas);
	}

	public static void personagem(Personagem personagem) {
		List<Projeto> projetos = Projeto.find("byPro_cod_usuario",
				renderArgs.get("user", Usuario.class).usu_codigo).fetch();

		int qtdProjetos = projetos.size();
		if (personagem == null) {
			render(projetos, qtdAmigosL, qtdProjetos);
		}
		Projeto projeto = Projeto.find("byPro_codigo", personagem.pro_codigo)
				.first();
		render(projetos, qtdAmigosL, qtdProjetos, personagem, projeto);
	}

	public static void nota(Nota nota) {
		List<Projeto> projetos = Projeto.find("byPro_cod_usuario",
				renderArgs.get("user", Usuario.class).usu_codigo).fetch();
		int qtdProjetos = projetos.size();
		if (nota == null) {
			render(projetos, qtdAmigosL, qtdProjetos);
		}
		Projeto projeto = Projeto.find("byPro_codigo", nota.pro_codigo).first();

		render(projetos, qtdAmigosL, qtdProjetos, nota, projeto);
	}

	public static void local(Local local) {
		List<Projeto> projetos = Projeto.find("byPro_cod_usuario",
				renderArgs.get("user", Usuario.class).usu_codigo).fetch();

		int qtdProjetos = projetos.size();
		if (local == null) {
			render(projetos, qtdAmigosL, qtdProjetos);
		}
		Projeto projeto = Projeto.find("byPro_codigo", local.pro_codigo)
				.first();
		render(projetos, qtdAmigosL, qtdProjetos, local, projeto);
	}

	public static void novoCapitulo(CapituloSolo capitulo) {
		Integer id = params.get("pro_codigo", Integer.class);
		Projeto projeto = Projeto.find("byPro_codigo", id).first();

		capitulo.cap_musica = "/public/sounds/" + capitulo.cap_musica;
		capitulo.pro_codigo = projeto.pro_codigo;
		capitulo.save();
		projeto.pro_capitulos++;
		projeto.save();
		/*
		 * Projeto_capitulo pros = new Projeto_capitulo();
		 * pros.capitulo_cap_codigo = capitulo.cap_codigo;
		 * pros.projeto_pro_codigo = projeto.pro_codigo; pros.save();
		 */
		Posts post = new Posts();
		post.pos_cod_usuario = projeto.pro_cod_usuario;
		post.pos_texto = renderArgs.get("user", Usuario.class).usu_nome
				+ " Iniciou o capitulo de titulo " + capitulo.cap_titulo;
		post.save();

		projeto(projeto);
	}

	public static void novoPersonagem(Personagem personagem) {
		Integer id = params.get("pro_codigo", Integer.class);
		System.out.println("teste asdadsa " + id);
		Projeto projeto = Projeto.find("byPro_codigo", id).first();
		personagem.pro_codigo = projeto.pro_codigo;
		personagem.save();
		projeto.pro_personagens++;
		projeto.save();
		Posts post = new Posts();
		post.pos_cod_usuario = projeto.pro_cod_usuario;
		post.pos_texto = renderArgs.get("user", Usuario.class).usu_nome
				+ " Criou o personagem  " + personagem.per_nome;
		post.save();

		projeto(projeto);
	}

	public static void novaNota(Nota nota) {
		Integer id = params.get("pro_codigo", Integer.class);
		System.out.println("teste asdadsa " + id);
		Projeto projeto = Projeto.find("byPro_codigo", id).first();
		nota.pro_codigo = projeto.pro_codigo;
		nota.save();
		projeto.pro_notas++;
		projeto.save();
		Posts post = new Posts();
		post.pos_cod_usuario = projeto.pro_cod_usuario;
		post.pos_texto = renderArgs.get("user", Usuario.class).usu_nome
				+ " Criou a nota " + nota.not_titulo;
		post.save();

		projeto(projeto);
	}

	public static void novoLocal(Local local) {
		Integer id = params.get("pro_codigo", Integer.class);
		System.out.println("teste asdadsa " + id);
		Projeto projeto = Projeto.find("byPro_codigo", id).first();
		local.pro_codigo = projeto.pro_codigo;
		local.save();
		projeto.pro_notas++;
		projeto.save();
		Posts post = new Posts();
		post.pos_cod_usuario = projeto.pro_cod_usuario;
		post.pos_texto = renderArgs.get("user", Usuario.class).usu_nome
				+ " Criou o local " + local.loc_nome;
		post.save();

		projeto(projeto);
	}

	public static void addCapa(Projeto projeto) {
		render(projeto);
	}

	public static void salvarCapa(Projeto projeto) {

		projeto.save();
		index(qtdAmigosL, 1);
	}

	public static void projetoPhoto(int id) {
		final Projeto projeto = Projeto.findById(id);
		notFoundIfNull(projeto);
		response.setContentTypeIfNotSet(projeto.pro_imagem.type());
		renderBinary(projeto.pro_imagem.get());
	}
}
