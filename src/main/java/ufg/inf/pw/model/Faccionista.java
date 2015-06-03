package ufg.inf.pw.model;
// Generated Jun 3, 2015 1:24:18 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Faccionista generated by hbm2java
 */
public class Faccionista  implements java.io.Serializable {


     private Integer idfaccionista;
     private String nome;
     private String telefone;
     private String celular;
     private String endereco;
     private String cep;
     private String cidade;
     private String email;
     private Date dataNascimento;
     private Set faccaos = new HashSet(0);

    public Faccionista() {
    }

    public Faccionista(String nome, String telefone, String celular, String endereco, String cep, String cidade, String email, Date dataNascimento, Set faccaos) {
       this.nome = nome;
       this.telefone = telefone;
       this.celular = celular;
       this.endereco = endereco;
       this.cep = cep;
       this.cidade = cidade;
       this.email = email;
       this.dataNascimento = dataNascimento;
       this.faccaos = faccaos;
    }
   
    public Integer getIdfaccionista() {
        return this.idfaccionista;
    }
    
    public void setIdfaccionista(Integer idfaccionista) {
        this.idfaccionista = idfaccionista;
    }
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return this.telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getCelular() {
        return this.celular;
    }
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getEndereco() {
        return this.endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getCep() {
        return this.cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getCidade() {
        return this.cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getDataNascimento() {
        return this.dataNascimento;
    }
    
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public Set getFaccaos() {
        return this.faccaos;
    }
    
    public void setFaccaos(Set faccaos) {
        this.faccaos = faccaos;
    }




}


