package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name="estabelecimento")
public class Estabelecimento extends GenericModel{
	
	@Id
	@GeneratedValue
	public Integer est_codigo;
	public String est_nome;
	public String est_descricao;
	public String est_caminho_imagem;
	public Double est_lat;
	public Double est_lon;
	public String est_logo;
	@Transient
	public List<Usuario> usuarios = new ArrayList<Usuario>();
	 
}
