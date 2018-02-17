package ufg.inf.pw.beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.RowEditEvent;
import ufg.inf.pw.DAO.FaccionistaDAO;
import ufg.inf.pw.model.Faccionista;

@ManagedBean
@SessionScoped
public class FaccionistaBean extends AbstractBean
        implements Serializable {

    private Faccionista faccionista;
    private Faccionista selectedFaccionista;
    private FaccionistaDAO faccionistaDAO;
    private List<Faccionista> faccionistas;
    private List<Faccionista> filteredFaccionistas;

    public FaccionistaBean() {
        faccionista = new Faccionista();
    }
    
    @PostConstruct
    public void init() {
        faccionistaDAO = new FaccionistaDAO();
    }

    public FaccionistaDAO getFaccionistaDAO() {
        return faccionistaDAO;
    }

    public void setFaccionistaDAO(FaccionistaDAO faccionistaDAO) {
        this.faccionistaDAO = faccionistaDAO;
    }

    public Faccionista getFaccionista() {
        return faccionista;
    }

    public void setFaccionista(Faccionista faccionista) {
        this.faccionista = faccionista;
    }

    public Faccionista getSelectedFaccionista() {
        return selectedFaccionista;
    }

    public void setSelectedFaccionista(Faccionista selectedFaccionista) {
        this.selectedFaccionista = selectedFaccionista;
    }

    public List getFaccionistas() {
        loadFaccionistas();
        return faccionistas;
    }

    public void setFaccionistas(List faccionistas) {
        this.faccionistas = faccionistas;
    }

    public List getFilteredFaccionistas() {
        return filteredFaccionistas;
    }

    public void setFilteredFaccionistas(List filteredFaccionistas) {
        this.filteredFaccionistas = filteredFaccionistas;
    }

    private void loadFaccionistas() {
        faccionistas = getFaccionistaDAO().findAll();
    }

    private void resetFaccionista() {
        faccionista = new Faccionista();
    }

    public String create() {
        try {
            getFaccionistaDAO().persist(faccionista);
            displayInfoMessageToUser("Faccionista cadastrada com sucesso!");
            loadFaccionistas();
            resetFaccionista();
            return "faccionista-list";
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao cadastrar a nova faccionista!");
            System.out.println(e);
            return "faccionista-new";
        }
    }

    public String newFaccionista() {
        return "faccionista-new";
    }

    public List findAll() {
        loadFaccionistas();
        return faccionistas;
    }

    public String remove() {
        try {
            System.out.println("entrou");
            getFaccionistaDAO().remove(selectedFaccionista);
            displayInfoMessageToUser("Faccionista exclu\355da com sucesso!");
            loadFaccionistas();
            resetFaccionista();
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao excluir faccionista!");
            System.out.println(e);
        }
        return "faccionista-list";
    }

    public int getNextId() {
        return getFaccionistaDAO().getNextId();
    }

    public void onRowEdit(RowEditEvent event) {
        Faccionista faccionistaAlterado = (Faccionista) event.getObject();
        getFaccionistaDAO().merge(faccionistaAlterado);
        displayInfoMessageToUser("Faccionista atualizada com sucesso!");
    }

    public void onCancel(RowEditEvent event) {
        displayInfoMessageToUser("Atualização cancelada com sucesso!");
    }

}
