package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;

@Entity
@Table(name="biblioteca")
public class Biblioteca extends GenericModel {
	
	@Id
	@GeneratedValue
	public Integer id;
	public Integer usu_codigo;
	public Integer pro_codigo;
	
}
