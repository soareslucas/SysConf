package ufg.inf.pw.model;
// Generated Jun 3, 2015 1:24:18 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Corte generated by hbm2java
 */
public class Corte  implements java.io.Serializable {


     private Integer idcorte;
     private String nome;
     private String foto;
     private Set modelos = new HashSet(0);

    public Corte() {
    }

    public Corte(String nome, String foto, Set modelos) {
       this.nome = nome;
       this.foto = foto;
       this.modelos = modelos;
    }
   
    public Integer getIdcorte() {
        return this.idcorte;
    }
    
    public void setIdcorte(Integer idcorte) {
        this.idcorte = idcorte;
    }
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getFoto() {
        return this.foto;
    }
    
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public Set getModelos() {
        return this.modelos;
    }
    
    public void setModelos(Set modelos) {
        this.modelos = modelos;
    }




}


