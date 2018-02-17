package ufg.inf.pw.beans;

import java.io.Serializable;
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
import ufg.inf.pw.DAO.UnidadeDAO;
import ufg.inf.pw.model.Unidade;

@ManagedBean
@SessionScoped
public class UnidadeBean extends AbstractBean
        implements Serializable {

    private Unidade unidade;
    private Unidade selectedUnidade;
    private UnidadeDAO unidadeDAO;
    private List<Unidade> unidades;
    private List<Unidade> filteredUnidades;
    private UploadedFile fotoUploaded;

    public UnidadeBean() {
        unidade = new Unidade();
    }

    @PostConstruct
    public void init() {
        unidadeDAO = new UnidadeDAO();
    }

    public UnidadeDAO getUnidadeDAO() {
        return unidadeDAO;
    }

    public void setUnidadeDAO(UnidadeDAO unidadeDAO) {
        this.unidadeDAO = unidadeDAO;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public Unidade getSelectedUnidade() {
        return selectedUnidade;
    }

    public void setSelectedUnidade(Unidade selectedUnidade) {
        this.selectedUnidade = selectedUnidade;
    }

    public List getUnidades() {
        loadUnidades();
        return unidades;
    }

    public void setUnidades(List unidades) {
        this.unidades = unidades;
    }

    public List getFilteredUnidades() {
        return filteredUnidades;
    }

    public void setFilteredUnidades(List filteredUnidades) {
        this.filteredUnidades = filteredUnidades;
    }

    public UploadedFile getFotoUploaded() {
        return fotoUploaded;
    }

    public void setFotoUploaded(UploadedFile foto) {
        fotoUploaded = foto;
    }

    private void loadUnidades() {
        unidades = getUnidadeDAO().findAll();
    }

    private void resetUnidade() {
        unidade = new Unidade();
    }

    public String create() {
        try {
            getUnidadeDAO().persist(unidade);
            displayInfoMessageToUser("Unidade cadastrado com sucesso!");
            loadUnidades();
            resetUnidade();
            return "unidade-new";
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao cadastrar a novo unidade!");
            resetUnidade();
            System.out.println(e);
            return "unidade-new";
        }
    }

    public String newUnidade() {
        resetUnidade();
        return "unidade-new";
    }

    public List findAll() {
        loadUnidades();
        return unidades;
    }

    public String remove() {
        try {
            getUnidadeDAO().remove(selectedUnidade);
            displayInfoMessageToUser("Unidade excl\372ida com sucesso!");
            loadUnidades();
            resetUnidade();
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao excluir unidade!");
            System.out.println(e);
        }
        return "unidade-new";
    }

    public String update() {
        getUnidadeDAO().merge(selectedUnidade);
        displayInfoMessageToUser("Unidade atualizado com sucesso!");
        return "unidade-list";
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
