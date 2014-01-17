package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="projeto_capitulo")
public class Projeto_capitulo extends Model {
	
	@Id
	public Integer projeto_pro_codigo;
	public Integer capitulo_cap_codigo;
}
