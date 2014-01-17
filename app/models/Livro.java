package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Blob;
import play.db.jpa.GenericModel;

@Entity
@Table(name="livro")
public class Livro  extends GenericModel{
	@Id
	@GeneratedValue
	public Integer liv_codigo;
	public String liv_titulo = " livro ";
	public String liv_texto;
	public String liv_cod_usuario;
	public Blob liv_imagem;

}
