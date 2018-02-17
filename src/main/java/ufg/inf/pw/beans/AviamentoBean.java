package ufg.inf.pw.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import ufg.inf.pw.DAO.AviamentoDAO;
import ufg.inf.pw.DAO.UnidadeDAO;
import ufg.inf.pw.model.Aviamento;
import ufg.inf.pw.model.Unidade;

@ManagedBean
@SessionScoped
public class AviamentoBean extends AbstractBean
        implements Serializable {

    private Aviamento aviamento;
    private Aviamento selectedAviamento;
    private AviamentoDAO aviamentoDAO;
    private UnidadeDAO modeloDAO;
    private List<Aviamento> aviamentos;
    private List<Unidade> unidades;
    private List<Aviamento> filteredAviamentos;
    private UploadedFile fotoUploaded;
    private Unidade selectedUnidade;

    public AviamentoBean() {
        aviamento = new Aviamento();
    }

    @PostConstruct
    public void init() {
        aviamentoDAO = new AviamentoDAO();
        modeloDAO = new UnidadeDAO();
    }

    public AviamentoDAO getAviamentoDAO() {
        return aviamentoDAO;
    }

    public void setAviamentoDAO(AviamentoDAO aviamentoDAO) {
        this.aviamentoDAO = aviamentoDAO;
    }

    public UnidadeDAO getUnidadeDAO() {
        return modeloDAO;
    }

    public void setUnidadeDAO(UnidadeDAO modeloDAO) {
        this.modeloDAO = modeloDAO;
    }

    public Aviamento getAviamento() {
        return aviamento;
    }

    public void setAviamento(Aviamento aviamento) {
        this.aviamento = aviamento;
    }

    public Aviamento getSelectedAviamento() {
        return selectedAviamento;
    }

    public void setSelectedAviamento(Aviamento selectedAviamento) {
        this.selectedAviamento = selectedAviamento;
    }

    public List getAviamentos() {
        loadAviamentos();
        return aviamentos;
    }

    public void setAviamentos(List aviamentos) {
        this.aviamentos = aviamentos;
    }

    public List getFilteredAviamentos() {
        return filteredAviamentos;
    }

    public void setFilteredAviamentos(List filteredAviamentos) {
        this.filteredAviamentos = filteredAviamentos;
    }

    public UploadedFile getFotoUploaded() {
        return fotoUploaded;
    }

    public void setFotoUploaded(UploadedFile foto) {
        fotoUploaded = foto;
    }

    public List getUnidades()
            throws SQLException {
        this.unidades = getUnidadeDAO().findAll();
        return this.unidades;
    }

    public void setUnidades(List unidades) {
        this.unidades = unidades;
    }

    public Unidade getSelectedUnidade() {
        return selectedUnidade;
    }

    public void setSelectedUnidade(Unidade selectedUnidade) {
        this.selectedUnidade = selectedUnidade;
    }

    private void loadAviamentos() {
        aviamentos = getAviamentoDAO().findAll();
    }

    private void loadUnidades() {
        unidades = getUnidadeDAO().findAll();
    }

    private void resetAviamento() {
        aviamento = new Aviamento();
        aviamento.setUnidade(new Unidade());
    }

    public String create() {
        try {
            getAviamentoDAO().persist(aviamento);
            displayInfoMessageToUser("Aviamento cadastrado com sucesso!");
            loadAviamentos();
            resetAviamento();
            return "aviamento-list";
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao cadastrar a novo aviamento!");
            System.out.println(e);
            return "aviamento-new";
        }
    }

    public String newAviamento() {
        resetAviamento();
        loadUnidades();
        return "aviamento-new";
    }

    public List findAll() {
        loadAviamentos();
        return aviamentos;
    }

    public String remove() {
        try {
            getAviamentoDAO().remove(selectedAviamento);
            displayInfoMessageToUser("Aviamento exclu\355do com sucesso!");
            loadAviamentos();
            resetAviamento();
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao excluir aviamento!");
            System.out.println(e);
        }
        return "aviamento-list";
    }

    public int getNextId() {
        return getAviamentoDAO().getNextId();
    }

    public String update() {
        getAviamentoDAO().merge(selectedAviamento);
        displayInfoMessageToUser("Aviamento atualizado com sucesso!");
        return "aviamento-list";
    }

    public void onCancel(RowEditEvent event) {
        displayInfoMessageToUser("Atualiza\347\343o cancelada com sucesso!");
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

}
