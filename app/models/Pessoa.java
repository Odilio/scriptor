/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.Blob;
import play.db.jpa.Model;
@Entity
@Table(name="pessoa")
public class Pessoa extends Model  {
	 @Id
	public Integer pes_codigo;
    public String pes_nome;
     public String pes_email;
     public Integer pes_usu_codigo;
     public String pes_twitter;
    public String pes_site;  
     public String pes_fone_cel;
    public String pesMunicipio;
    public Blob pesFoto;
  
   
}