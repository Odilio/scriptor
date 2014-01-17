package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Blob;
import play.db.jpa.GenericModel;

@Entity
@Table(name="personagem")
public class Personagem  extends GenericModel   {

	@Id
	@GeneratedValue
	public Integer per_codigo;
	public String per_nome ;
	public String per_descricao ;
	public String per_referencia;
	public Blob per_imagem;
	@Column(name="projeto_pro_codigo")
	public Integer pro_codigo;
	
}
