package ufg.inf.pw.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import ufg.inf.pw.DAO.CorteDAO;
import ufg.inf.pw.DAO.ModeloDAO;
import ufg.inf.pw.model.Corte;
import ufg.inf.pw.model.Modelo;
import ufg.inf.pw.utils.FotoUpload;

@ManagedBean
@SessionScoped
public class CorteBean extends AbstractBean
        implements Serializable {

    private Corte corte;
    private Corte selectedCorte;
    private CorteDAO corteDAO;
    private ModeloDAO modeloDAO;
    private List<Corte> cortes;
    private List<Modelo> modelos;
    private List<Corte> filteredCortes;
    private UploadedFile fotoUploaded;
    private Modelo selectedModelo;

    public CorteBean() {
        corte = new Corte();
    }

    @PostConstruct
    public void init() {
        corteDAO = new CorteDAO();
        modeloDAO = new ModeloDAO();
    }

    public CorteDAO getCorteDAO() {
        return corteDAO;
    }

    public void setCorteDAO(CorteDAO corteDAO) {
        this.corteDAO = corteDAO;
    }

    public ModeloDAO getModeloDAO() {
        return modeloDAO;
    }

    public void setModeloDAO(ModeloDAO modeloDAO) {
        this.modeloDAO = modeloDAO;
    }

    public Corte getCorte() {
        return corte;
    }

    public void setCorte(Corte corte) {
        this.corte = corte;
    }

    public Corte getSelectedCorte() {
        return selectedCorte;
    }

    public void setSelectedCorte(Corte selectedCorte) {
        this.selectedCorte = selectedCorte;
    }

    public List getCortes() {
        loadCortes();
        return cortes;
    }

    public void setCortes(List cortes) {
        this.cortes = cortes;
    }

    public List getFilteredCortes() {
        return filteredCortes;
    }

    public void setFilteredCortes(List filteredCortes) {
        this.filteredCortes = filteredCortes;
    }

    public UploadedFile getFotoUploaded() {
        return fotoUploaded;
    }

    public void setFotoUploaded(UploadedFile foto) {
        fotoUploaded = foto;
    }

    public List<Modelo> getModelos()
            throws SQLException {
        
        this.modelos = getModeloDAO().findAllJDBC();
        return this.modelos;
    }

    public void setModelos(List modelos) {
        this.modelos = modelos;
    }

    public Modelo getSelectedModelo() {
        return selectedModelo;
    }

    public void setSelectedModelo(Modelo selectedModelo) {
        this.selectedModelo = selectedModelo;
    }

    private void loadCortes() {
        cortes = getCorteDAO().findAll();
    }

    private void loadModelos() {
        try {
            modelos = getModeloDAO().findAllJDBC();
        } catch (SQLException ex) {
            Logger.getLogger(ufg.inf.pw.model.Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resetCorte() {
        corte = new Corte();
        corte.setModelo(new Modelo());
    }

    public String create() {
        try {
            getCorteDAO().persist(corte);
            displayInfoMessageToUser("Corte cadastrado com sucesso!");
            loadCortes();
            resetCorte();
            return "corte-list";
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao cadastrar a novo corte!");
            System.out.println(e);
            return "corte-new";
        }
    }

    public String newCorte() {
        resetCorte();
        loadModelos();
        return "corte-new";
    }

    public List findAll() {
        loadCortes();
        return cortes;
    }

    public String remove() {
        try {
            getCorteDAO().remove(selectedCorte);
            displayInfoMessageToUser("Corte excluídos com sucesso!");
            loadCortes();
            resetCorte();
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao excluir corte!");
            System.out.println(e);
        }
        return "corte-list";
    }

    public int getNextId() {
        return getCorteDAO().getNextId();
    }

    public String update() {
        getCorteDAO().merge(selectedCorte);
        displayInfoMessageToUser("Corte atualizado com sucesso!");
        return "corte-list";
    }

    public void onCancel(RowEditEvent event) {
        displayInfoMessageToUser("Atualização cancelada com sucesso!");
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void setFotoCorte()
            throws Exception {
        corte.setFoto(FotoUpload.FileUploadToImg64(fotoUploaded));
        System.out.println(corte.getFoto());
        displayInfoMessageToUser((new StringBuilder()).append("A foto ").append(fotoUploaded.getFileName()).append(" foi carregada.").toString());
    }

    public void setFotoCorteEdit()
            throws Exception {
        String foto = FotoUpload.FileUploadToImg64(fotoUploaded);
        corte.setFoto(foto);
        selectedCorte.setFoto(foto);
    }

}
