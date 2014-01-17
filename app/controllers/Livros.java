package controllers;

import static play.libs.F.Matcher.ClassOf;
import static play.libs.F.Matcher.Equals;
import static play.mvc.Http.WebSocketEvent.SocketClosed;
import static play.mvc.Http.WebSocketEvent.TextFrame;

import java.util.ArrayList;
import java.util.List;

import models.Biblioteca;
import models.Capitulo;
import models.CapituloSolo;
import models.ChatRoom;
import models.Comentario;
import models.Livro;
import models.Local;
import models.Local_capitulo;
import models.Personagem;
import models.Personagem_capitulo;
import models.Posts;
import models.Projeto;
import models.Projeto_capitulo;
import models.Recomendar;
import models.Usuario;
import play.cache.Cache;
import play.libs.F.Either;
import play.libs.F.EventStream;
import play.libs.F.Promise;
import play.mvc.Before;
import play.mvc.WebSocketController;
import play.mvc.Http.WebSocketClose;
import play.mvc.Http.WebSocketEvent;

public class Livros extends Application {
	
	@Before
	    static void checkUser() {
	        if(connected() == null) {
	            flash.error("Please log in first");
	            Application.index2();
	        }
	    }
	 
	 public static void index(List<Projeto> livros) {
		 String titulo = params.get("titulo", String.class);
		 
		 String autor = params.get("autor", String.class);
		 String pro_genero = params.get("pro_genero", String.class);
		 
		 String pro_tipo = params.get("pro_tipo", String.class);
		 if(titulo != null || autor != null || pro_genero != null || pro_tipo != null) {
			 titulo = titulo.toLowerCase();
			 autor = autor.toLowerCase();
			 pro_genero = pro_genero.toLowerCase();
			 pro_tipo = pro_tipo.toLowerCase();
			 if (pro_genero.equalsIgnoreCase("todos")){
				 livros = Projeto.find("lower(pro_titulo) like ? and lower(pro_nome_usuario) like ?  and lower(pro_tipo) like ? and pro_visivel = true", "%"+titulo+"%", "%"+autor+"%", "%"+pro_tipo+"%").fetch();
			 }else{
			     livros = Projeto.find("lower(pro_titulo) like ? and lower(pro_nome_usuario) like ? and lower(pro_genero) like ? and lower(pro_tipo) like ? and pro_visivel = true", "%"+titulo+"%", "%"+autor+"%", "%"+pro_genero+"%", "%"+pro_tipo+"%").fetch();
			 }
		 }
	   	 /* List<Livro> livros = Cache.get("livros", List.class);
	   	  * 
	   	  */
	   	  if(livros == null) {
		 livros = Projeto.find("pro_visivel = ? order by pro_recomenda desc", true).fetch(16);
	   	  }
	         /*    Cache.set("livros", livros, "30mn");
	         }*/
	   	 System.out.println("livros passou a bagaceira "+ livros.size());
	       render(livros);
	   }
	
	 
	 public static void buscar(String titulo,String tituloa,String tituloe) {
		 System.out.println("livros inicio");
		 titulo = params.get("titulo");
		 if (titulo != "" && titulo != null){
		titulo = titulo.toLowerCase();
		 List<Projeto> livros = Projeto.find("lower(pro_titulo) like ?", "%"+titulo+"%").fetch();
	         /*    Cache.set("livros", livros, "30mn");
	         }*/
		 index(livros);
		 }
			 List<Projeto> livros = Projeto.findAll();
		 
		
		 index(livros);
	   }
	 
	 public static void recomendar(Projeto projeto) {
		 Recomendar biblio = new Recomendar();
		 biblio.usu_codigo = renderArgs.get("user", Usuario.class).usu_codigo;
		 biblio.pro_codigo = projeto.pro_codigo;
		 biblio.save();
		 projeto.pro_recomenda ++;
		 projeto.save();
		 Posts post = new Posts();
	     post.pos_cod_usuario = projeto.pro_cod_usuario;
		 post.pos_texto = renderArgs.get("user", Usuario.class).usu_nome_completo
					+ " Recomendou o livro de titulo " + projeto.pro_titulo;
			
		 post.pos_nome = projeto.pro_nome_usuario;
		 post.save();
		 perfil(projeto);
	 }
	 
	 
	 public static void excluirRecomendar(Projeto projeto) {
		 Recomendar bibli = Recomendar.find("usu_codigo = ? and pro_codigo = ?", renderArgs.get("user", Usuario.class).usu_codigo, projeto.pro_codigo ).first();
		 bibli.delete();
		 projeto.pro_recomenda --;
		 projeto.save();
		 perfil(projeto);
	 }
	 
 public static void perfil(Projeto projeto) {
	 	boolean adicionado= false;
	 	boolean recomendado = false;
		Usuario usu = Usuario.find("byUsu_codigo", projeto.pro_cod_usuario).first();
		Biblioteca bibli = Biblioteca.find("usu_codigo = ? and pro_codigo = ?", renderArgs.get("user", Usuario.class).usu_codigo, projeto.pro_codigo ).first();
		Recomendar recomen = Recomendar.find("usu_codigo = ? and pro_codigo = ?", renderArgs.get("user", Usuario.class).usu_codigo, projeto.pro_codigo ).first();
		if (bibli == null)
			adicionado= false;
		else 
			adicionado= true;
		if (recomen == null)
			recomendado= false;
		else 
			recomendado= true;
		List<Comentario> comentarios = Comentario.find("byCom_pro_codigo", projeto.pro_codigo).fetch();
		 render(projeto,usu,comentarios,adicionado,recomendado);
	 }
 
 public static void postComment(Comentario coment) {
	 	coment.save();
	 	Projeto projeto = Projeto.find("byPro_codigo", coment.com_pro_codigo).first();
	 	perfil(projeto);
	}
 
 public static void ficha() {
	 	
	 	render();
	}
 
 public static void addBiblioteca(Projeto projeto) {
	 Biblioteca biblio = new Biblioteca();
	 biblio.usu_codigo = renderArgs.get("user", Usuario.class).usu_codigo;
	 biblio.pro_codigo = projeto.pro_codigo;
	 biblio.save();
	 Posts post = new Posts();
     post.pos_cod_usuario = projeto.pro_cod_usuario;
	 post.pos_texto = renderArgs.get("user", Usuario.class).usu_nome_completo
				+ " Adicionou o livro de titulo " + projeto.pro_titulo+" a sua biblioteca! ";
		
	 post.pos_nome = projeto.pro_nome_usuario;
	 post.save();
	 perfil(projeto);
	}
 
 public static void excluirBiblioteca(Projeto projeto) {
	 Biblioteca bibli = Biblioteca.find("usu_codigo = ? and pro_codigo = ?", renderArgs.get("user", Usuario.class).usu_codigo, projeto.pro_codigo ).first();
	 bibli.delete();
	 perfil(projeto);
 }
 
	 public static void livro(Projeto projeto) {
		 
		 List<Capitulo> capitulos = Capitulo.find("byPro_codigo", projeto.pro_codigo).fetch(); 
		 List<Personagem_capitulo> pers_caps;
		 Personagem per = null;
		 for (Capitulo capitulo : capitulos) {
			 pers_caps = Personagem_capitulo.find("byCap_codigo", capitulo.cap_codigo).fetch();
			 for (Personagem_capitulo personagem_capitulo : pers_caps) {
				 per = Personagem.find("byPer_codigo", personagem_capitulo.per_codigo).first();
				 capitulo.cap_texto =  capitulo.cap_texto.replaceAll(per.per_referencia, "<a href='index' >"+per.per_nome+"</a>");
			}
		}
		 if (projeto.pro_tipo.equalsIgnoreCase("solo") ){
			 livroLayout2(null);
		 }
		 render(projeto,capitulos);
	 }
	 
 public static void livroLayout2(Integer id) {
	 	 Integer pro_codigo = params.get("pro_codigo", Integer.class);
	 	 id = params.get("id", Integer.class);
	 	 Projeto projeto = null;
	 	 List<Capitulo> capitulos = null;
	 	int next =1;
	 	 int prev = 0;
	 	 Personagem per = null;
	 	 if(id == null) {
	 		projeto = Projeto.find("byPro_codigo", pro_codigo).first();
	 	 System.out.println("p id "+pro_codigo);
	 	 capitulos = Capitulo.find("byPro_codigo", pro_codigo).fetch(); 
	 	
	 	List<Personagem_capitulo> pers_caps;
		
		 for (Capitulo capitulo : capitulos) {
			 pers_caps = Personagem_capitulo.find("byCap_codigo", capitulo.cap_codigo).fetch();
			 for (Personagem_capitulo personagem_capitulo : pers_caps) {
				 per = Personagem.find("byPer_codigo", personagem_capitulo.per_codigo).first();
				 capitulo.cap_texto =  capitulo.cap_texto.replaceAll(per.per_referencia, "<a href='index' >"+per.per_nome+"</a>");
			}
		}
		 if(capitulos.size()>0){
		 Cache.set("capitulos", capitulos, "30mn");
		 Cache.set("proLivro", projeto, "30mn");
		 Capitulo capAtual = capitulos.get(0);
		 render(projeto,capAtual,prev,next);
		 }else{
			 
			 manutencao(projeto);
		 }

	 	 }
	 	 projeto =  Cache.get("proLivro", Projeto.class);
	 	capitulos =  Cache.get("capitulos", List.class);
	 	next = id+1;
	 	  prev = id-1;
	 	 Capitulo capAtual = capitulos.get(id);
		 render(projeto,capAtual,next,prev);
	 }
 
	 
 public static void livroInterativo(Integer id) {
	 boolean musica = true;
 	 Integer pro_codigo = params.get("pro_codigo", Integer.class);
 	 id = params.get("id", Integer.class);
 	 Projeto projeto = null;
 	 List<CapituloSolo> capitulos = null;
 	 Personagem per = null;
 	 Local local = null;
 	 populaCapituloSolo(id, pro_codigo, per);
 	 if (id == 0 )
 		 id= 1;
 	 projeto =  Cache.get("proLivro", Projeto.class);
 	capitulos =  Cache.get("CapituloSolo", List.class);
 	per = Cache.get("per", Personagem.class);
 	CapituloSolo capAtual = capitulos.get(id-1);
 	 if (capAtual.cap_musica.equalsIgnoreCase("Sem Trilha")){
 		 musica = false;
 	 }
	 render(projeto,capAtual,per,musica);
 }

private static void populaCapituloSolo(Integer id, Integer pro_codigo,
		Personagem per) {
	Projeto projeto;
	List<CapituloSolo> capitulos;
	Local local;
	if(id == null) {
 		projeto = Projeto.find("byPro_codigo", pro_codigo).first();
 	 capitulos = CapituloSolo.find("pro_codigo = ? order by cap_indice", pro_codigo).fetch(); 
 	
 	List<Personagem_capitulo> pers_caps = null;
 	List<Local_capitulo> loca_caps = null;
	 for (CapituloSolo capitulo : capitulos) {
		 pers_caps = Personagem_capitulo.find("byCap_codigo", capitulo.cap_codigo).fetch();
		 for (Personagem_capitulo personagem_capitulo : pers_caps) {
			 per = Personagem.find("byPer_codigo", personagem_capitulo.per_codigo).first();
			 capitulo.cap_texto =  capitulo.cap_texto.replaceAll(per.per_referencia, "<a href='index' >"+per.per_nome+"</a>");
		}
		 loca_caps = Local_capitulo.find("byCapitulo_cap_codigo", capitulo.cap_codigo).fetch();
		 for (Local_capitulo personagem_capitulo : loca_caps) {
			 local = Local.find("byLoc_codigo", personagem_capitulo.local_loc_codigo).first();
			 capitulo.cap_texto =  capitulo.cap_texto.replaceAll(local.loc_referencia, local.loc_maps);
		}
	}
	 if(capitulos.size()>0){
	 Cache.set("CapituloSolo", capitulos, "30mn");
	 Cache.set("proLivro", projeto, "30mn");
	 CapituloSolo capAtual = capitulos.get(0);
	 Cache.set("per", per, "30mn");
	 render(projeto,capAtual,per);
	 }else{
		 
		 manutencao(projeto);
	 }
 	 }
}
 
 
 
 public static void manutencao(Projeto projeto) {
	
	render(projeto);
	
}

public static void personPhoto(int id) {
	   final Personagem user = Personagem.findById(id);
	   notFoundIfNull(user);
	   response.setContentTypeIfNotSet(user.per_imagem.type());
	   renderBinary(user.per_imagem.get());
	}

 
	 public static void room() {
		 Integer pro_codigo = params.get("pro_codigo", Integer.class);
		 String usu = params.get("usu_nome_completo", String.class);
		 System.out.println(usu);
	        render(usu,pro_codigo);
	    }
	 

	 public static class ChatRoomSocket extends WebSocketController {
	        
	        public static void join(String usu,Integer codigo) {
	            
	            ChatRoom room = ChatRoom.get(codigo);
	            
	            // Socket connected, join the chat room
	            EventStream<ChatRoom.Event> roomMessagesStream = room.join(usu);
	         
	            // Loop while the socket is open
	            while(inbound.isOpen()) {
	                
	                // Wait for an event (either something coming on the inbound socket channel, or ChatRoom messages)
	                Either<WebSocketEvent,ChatRoom.Event> e = await(Promise.waitEither(
	                    inbound.nextEvent(), 
	                    roomMessagesStream.nextEvent()
	                ));
	                
	                // Case: usu typed 'quit'
	                for(String userMessage: TextFrame.and(Equals("quit")).match(e._1)) {
	                    room.leave(usu);
	                    outbound.send("quit:ok");
	                    disconnect();
	                }
	                
	                // Case: TextEvent received on the socket
	                for(String userMessage: TextFrame.match(e._1)) {
	                    room.say(usu, userMessage);
	                }
	                
	                // Case: Someone joined the room
	                for(ChatRoom.Join joined: ClassOf(ChatRoom.Join.class).match(e._2)) {
	                    outbound.send("join:%s", joined.user);
	                }
	                
	                // Case: New message on the chat room
	                for(ChatRoom.Message message: ClassOf(ChatRoom.Message.class).match(e._2)) {
	                    outbound.send("message:%s:%s", message.user, message.text);
	                }
	                
	                // Case: Someone left the room
	                for(ChatRoom.Leave left: ClassOf(ChatRoom.Leave.class).match(e._2)) {
	                    outbound.send("leave:%s", left.user);
	                }
	                
	                // Case: The socket has been closed
	                for(WebSocketClose closed: SocketClosed.match(e._1)) {
	                    room.leave(usu);
	                    disconnect();
	                }
	                
	            }
	            
	        }

	 }
}
