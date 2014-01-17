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
@Table(name="nota")
public class Nota  extends GenericModel  {
	@Id
	@GeneratedValue
	public Integer not_codigo;
	public String not_titulo ;
	public String not_texto ;
	@Column(name="projeto_pro_codigo")
	public Integer pro_codigo;
	//@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})


}
