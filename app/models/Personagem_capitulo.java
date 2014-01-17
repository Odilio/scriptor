package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="personagem_capitulo")
public class Personagem_capitulo extends Model {
	
	@Id
	public Integer per_codigo;
	public Integer cap_codigo;
	public String per_nome;
}
