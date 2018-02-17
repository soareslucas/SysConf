package ufg.inf.pw.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.UploadedFile;
import ufg.inf.pw.DAO.ModeloDAO;
import ufg.inf.pw.model.Modelo;
import ufg.inf.pw.utils.FotoUpload;

@ManagedBean
@SessionScoped
public class ModeloBean extends AbstractBean
        implements Serializable {

    private Modelo modelo;
    private Modelo selectedModelo;
    private ModeloDAO modeloDAO;
    private List<Modelo> modelos;
    private List<Modelo> filteredModelos;
    private UploadedFile fotoUploaded;

    public ModeloBean() {
        modelo = new Modelo();
    }

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

    public List getModelos() {
        loadModelos();
        return modelos;
    }

    public void setModelos(List modelos) {
        this.modelos = modelos;
    }

    public List getFilteredModelos() {
        return filteredModelos;
    }

    public void setFilteredModelos(List filteredModelos) {
        this.filteredModelos = filteredModelos;
    }

    public UploadedFile getFotoUploaded() {
        return fotoUploaded;
    }

    public void setFotoUploaded(UploadedFile foto) {
        fotoUploaded = foto;
    }

    private void loadModelos() {
        try {
            modelos = getModeloDAO().findAllJDBC();
        } catch (SQLException ex) {
            Logger.getLogger(ufg.inf.pw.model.Modelo.class.getName()).log(Level.SEVERE, null, ex);
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

    public List findAll() {
        loadModelos();
        return modelos;
    }

    public String remove() {
        try {
            System.out.println("entrou");
            getModeloDAO().remove(selectedModelo);
            displayInfoMessageToUser("Modelo exclu\355da com sucesso!");
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

    public void setFotoModelo()
            throws Exception {
        modelo.setFoto(FotoUpload.FileUploadToImg64(fotoUploaded));
        System.out.println(modelo.getFoto());
    }

    public void setFotoModeloEdit()
            throws Exception {
        selectedModelo.setFoto(FotoUpload.FileUploadToImg64(fotoUploaded));
        System.out.println(modelo.getFoto());
    }

}
