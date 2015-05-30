/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufg.inf.pw.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import ufg.inf.pw.DAO.FaccionistaDAO;
import ufg.inf.pw.model.Faccionista;

/**
 *
 * @author Lucas
 */
@Named(value = "faccionistaBean")
@ViewScoped
public class FaccionistaBean extends AbstractBean {

    private Faccionista faccionista = new Faccionista();
    ;
    private Faccionista selectedFaccionista;
    private FaccionistaDAO faccionistaDAO;
    private List<Faccionista> faccionistas;
    private List<Faccionista> filteredFaccionistas;

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

    public List<Faccionista> getFaccionistas() {
        loadFaccionistas();
        return faccionistas;
    }

    public void setFaccionistas(List<Faccionista> faccionistas) {
        this.faccionistas = faccionistas;
    }

    public List<Faccionista> getFilteredFaccionistas() {
        return filteredFaccionistas;
    }

    public void setFilteredFaccionistas(List<Faccionista> filteredFaccionistas) {
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
           
//            Date dt = new Date();
//            faccionista.setCelular(" ");
//            faccionista.setCep(" ");
//            faccionista.setCidade("Goiânia");
//            faccionista.setDataNascimento(dt);
//            faccionista.setEmail(" ");
//            faccionista.setEndereco(" ");
//            faccionista.setTelefone(" ");
//            faccionista.setNome("José João Pôsto");

            System.out.println("entrou");
            getFaccionistaDAO().persist(faccionista);
            System.out.println("persistiu");
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

    public List<Faccionista> findAll() {
        loadFaccionistas();
        return faccionistas;
    }

    public String remove() {
        try {
            System.out.println("entrou");
            getFaccionistaDAO().remove(selectedFaccionista);
            displayInfoMessageToUser("Faccionista excluída com sucesso!");
            loadFaccionistas();
            resetFaccionista();
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao excluir faccionista!");
            System.out.println(e);
        }
        return "faccionista-list";
    }

    public void onRowEdit(RowEditEvent event) {
        System.out.println("entrou");
        Faccionista faccionistaAlterado = (Faccionista) event.getObject();
        getFaccionistaDAO().merge(faccionistaAlterado);
        displayInfoMessageToUser("Faccionista atualizada com sucesso!");
    }

    public void onCancel(RowEditEvent event) {
        displayInfoMessageToUser("Atualização cancelada com sucesso!");
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

}
