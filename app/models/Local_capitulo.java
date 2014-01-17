package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="local_capitulo")
public class Local_capitulo extends Model {
	
	@Id
	public Integer local_loc_codigo;
	public Integer capitulo_cap_codigo;
	public String loc_nome; 
}
