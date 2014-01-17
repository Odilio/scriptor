package models;

import java.util.Date;

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
@Table(name="comentario")
public class Comentario  extends GenericModel  {
	@Id
	@GeneratedValue
	public Integer com_codigo;
	public String com_texto ;
	public Integer com_pro_codigo;
	public Integer com_usu_codigo;
	public String com_usu_name ;
	public Date com_data;


}
