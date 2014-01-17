package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Blob;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name="usuario")
public class Usuario extends GenericModel{

	@Id
	@GeneratedValue
	public Integer usu_codigo;
	public String usu_nome_completo;
	public String usu_nome;
	public Blob usu_ima;
	public String usu_senha;
}
