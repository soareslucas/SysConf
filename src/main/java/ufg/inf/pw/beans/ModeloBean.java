/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template fotoUploaded, choose Tools | Templates
 * and open the template in the editor.
 */
package ufg.inf.pw.beans;

import java.io.Serializable;
import java.sql.SQLException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import ufg.inf.pw.DAO.ModeloDAO;
import ufg.inf.pw.model.Modelo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.UploadedFile;
import ufg.inf.pw.utils.FotoUpload;

/**
 *
 * @author Lucas
 */
@ManagedBean
@SessionScoped
public class ModeloBean extends AbstractBean implements Serializable {

    private Modelo modelo = new Modelo();
    private Modelo selectedModelo;
    private ModeloDAO modeloDAO;
    private List<Modelo> modelos;
    private List<Modelo> filteredModelos;
    private UploadedFile fotoUploaded;

    @PostConstruct
    public void init() {
        modeloDAO = new ModeloDAO();
    }

    public ModeloDAO getModeloDAO() {
        return modeloDAO;
    }

    public void setModeloDAO(ModeloDAO modeloDAO) {
        this.modeloDAO = modeloDAO;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Modelo getSelectedModelo() {
        return selectedModelo;
    }

    public void setSelectedModelo(Modelo selectedModelo) {
        this.selectedModelo = selectedModelo;
    }

    public List<Modelo> getModelos() {
        loadModelos();
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }

    public List<Modelo> getFilteredModelos() {
        return filteredModelos;
    }

    public void setFilteredModelos(List<Modelo> filteredModelos) {
        this.filteredModelos = filteredModelos;
    }

    public UploadedFile getFotoUploaded() {
        return fotoUploaded;
    }

    public void setFotoUploaded(UploadedFile foto) {
        this.fotoUploaded = foto;
    }

    private void loadModelos() {
        try {
            modelos = getModeloDAO().findAllJDBC();
        } catch (SQLException ex) {
            Logger.getLogger(ModeloBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetModelo() {
        modelo = new Modelo();
    }

    public String create() {
        try {
            getModeloDAO().persist(modelo);
            displayInfoMessageToUser("Modelo cadastrada com sucesso!");
            loadModelos();
            resetModelo();
            return "modelo-list";
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao cadastrar a nova modelo!");
            System.out.println(e);
            return "modelo-new";
        }
    }

    public String newModelo() {
        resetModelo();
        return "modelo-new";
    }

    public List<Modelo> findAll() {
        loadModelos();
        return modelos;
    }

    public String remove() {
        try {
            System.out.println("entrou");
            getModeloDAO().remove(selectedModelo);
            displayInfoMessageToUser("Modelo excluída com sucesso!");
            loadModelos();
            resetModelo();
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao excluir modelo!");
            System.out.println(e);
        }
        return "modelo-list";
    }

    public int getNextId() {
        return getModeloDAO().getNextId();
    }

    public String update() {

        getModeloDAO().merge(selectedModelo);
        displayInfoMessageToUser("Modelo atualizado com sucesso!");
        return "modelo-list";
    }

    public void setFotoModelo() throws Exception {
        this.modelo.setFoto(FotoUpload.FileUploadToImg64(fotoUploaded));
        System.out.println(this.modelo.getFoto());
    }

    public void setFotoModeloEdit() throws Exception {
        this.selectedModelo.setFoto(FotoUpload.FileUploadToImg64(fotoUploaded));
        System.out.println(this.modelo.getFoto());

    }
}
