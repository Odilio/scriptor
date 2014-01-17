package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name="amigos")
public class Amigos extends GenericModel {
	
	@Id
	@GeneratedValue
	public Integer id;
	public Integer usu_codigo1;
	public Integer usu_codigo2;
	public Boolean ami_solicitada;
	public Boolean ami_aceita;
}
