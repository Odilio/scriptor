package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.db.jpa.GenericModel;





@Entity
@Table(name="musica")
public class Musica  extends GenericModel  {
	public Musica(int i, String string) {
		mus_codigo = i;
		mus_titulo = string;
	}
	@Id
	@GeneratedValue
	public Integer mus_codigo;
	public String mus_titulo ;
	public String mus_formato;


}
