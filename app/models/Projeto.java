package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.jpa.Blob;
import play.db.jpa.GenericModel;

@Entity
@Table(name="projeto")
public class Projeto  extends GenericModel {
	@Id
	@GeneratedValue
	public Integer pro_codigo;
	public String pro_titulo ;
	public String pro_texto;
	public Integer pro_cod_usuario;
	public String pro_nome_usuario;
	public String pro_genero ;
	public Integer pro_recomenda;
	public int pro_capitulos;
	public int pro_notas;
	public int pro_personagens;
	public int pro_itens;
	public int pro_locais;
	public String pro_tipo;
	public Boolean pro_visivel;
	public Blob pro_imagem;
	//@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})



}
