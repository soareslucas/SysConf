/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template fotoUploaded, choose Tools | Templates
 * and open the template in the editor.
 */
package ufg.inf.pw.beans;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import ufg.inf.pw.DAO.ModeloDAO;
import ufg.inf.pw.model.Modelo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.imageio.ImageIO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import sun.misc.BASE64Encoder;

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
        modelos = getModeloDAO().findAll();
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

    public void onRowEdit(RowEditEvent event) {
        System.out.println("entrou");
        Modelo modeloAlterado = (Modelo) event.getObject();
        getModeloDAO().merge(modeloAlterado);
        displayInfoMessageToUser("Modelo atualizado com sucesso!");
    }

    public void onCancel(RowEditEvent event) {
        displayInfoMessageToUser("Atualização cancelada com sucesso!");
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void setFotoModelo() throws IOException {
        System.out.println("entrou");
        if (fotoUploaded != null) {
            System.out.println("passou");
            FacesMessage message = new FacesMessage("Sucesso", "A foto " + fotoUploaded.getFileName() + " foi carregada.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            InputStream image = fotoUploaded.getInputstream();
            BufferedImage img = ImageIO.read(image);

            String imageString = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {
                ImageIO.write(img, "jpg", bos);
                byte[] imageBytes = bos.toByteArray();

                BASE64Encoder encoder = new BASE64Encoder();
                imageString = encoder.encode(imageBytes);

                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.modelo.setFoto(imageString);

            System.out.println(this.modelo.getFoto());

        }
    }

}
