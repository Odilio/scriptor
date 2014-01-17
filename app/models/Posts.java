package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Blob;
import play.db.jpa.GenericModel;

@Entity
@Table(name="posts")
public class Posts  extends GenericModel{
	@Id
	@GeneratedValue
	public Integer pos_codigo;
	public String pos_texto;
	public String pos_nome;
	public Integer pos_cod_usuario;
	public Blob pos_imagem;
	public String pos_url;
	
}
