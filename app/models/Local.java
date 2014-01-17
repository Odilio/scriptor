package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Blob;
import play.db.jpa.GenericModel;

@Entity
@Table(name="local")
public class Local  extends GenericModel   {

	@Id
	@GeneratedValue
	public Integer loc_codigo;
	public String loc_nome;
	public String loc_descricao;
	public String loc_referencia;
	public String loc_maps;
	public Blob loc_imagem;
	@Column(name="projeto_pro_codigo")
	public Integer pro_codigo;
}
