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
@Table(name="capitulo")
public class Capitulo  extends GenericModel  {
	@Id
	@GeneratedValue
	public Integer cap_codigo;
	public String cap_titulo ;
	public String cap_texto ;
	public int cap_notas;
	public int cap_personagens;
	public int cap_itens;
	public int cap_locais;
	public int cap_indice;
	public String cap_musica;
	public String cap_texto_OP1;
	public String cap_texto_OP2;
	public Integer cap_indice_OP1;
	public Integer cap_indice_OP2;
	@Column(name="projeto_pro_codigo")
	public Integer pro_codigo;
	
	//@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})


}
