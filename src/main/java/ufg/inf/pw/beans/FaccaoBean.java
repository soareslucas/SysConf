package ufg.inf.pw.beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import ufg.inf.pw.DAO.*;
import ufg.inf.pw.model.*;

@ManagedBean
@SessionScoped
public class FaccaoBean extends AbstractBean
        implements Serializable {

    private Faccao faccao;
    private Faccao selectedFaccao;
    private FaccaoDAO faccaoDAO;
    private List<Faccao> faccaos;
    private List<Faccao> filteredFaccaos;
    private Peca peca;
    private Suprimento suprimento;
    private ModeloDAO modeloDAO;
    private AviamentoDAO aviamentoDAO;
    private PecaDAO pecaDAO;
    private SuprimentoDAO suprimentoDAO;
    private List<Faccao> proximasColetas;

    public FaccaoBean() {
        faccao = new Faccao();
        peca = new Peca();
        suprimento = new Suprimento();
    }
    
    @PostConstruct
    public void init() {
        faccaoDAO = new FaccaoDAO();
        modeloDAO = new ModeloDAO();
        aviamentoDAO = new AviamentoDAO();
        pecaDAO = new PecaDAO();
        suprimentoDAO = new SuprimentoDAO();
    }

    private SuprimentoDAO getSuprimentoDAO() {
        return suprimentoDAO;
    }

    private PecaDAO getPecaDAO() {
        return pecaDAO;
    }

    private ModeloDAO getModeloDAO() {
        return modeloDAO;
    }

    private AviamentoDAO getAviamentoDAO() {
        return aviamentoDAO;
    }

    public FaccaoDAO getFaccaoDAO() {
        return faccaoDAO;
    }

    public void setFaccaoDAO(FaccaoDAO faccaoDAO) {
        this.faccaoDAO = faccaoDAO;
    }

    public Faccao getFaccao() {
        return faccao;
    }

    public void setFaccao(Faccao faccao) {
        this.faccao = faccao;
    }

    public Faccao getSelectedFaccao() {
        return selectedFaccao;
    }

    public void setSelectedFaccao(Faccao selectedFaccao) {
        this.selectedFaccao = selectedFaccao;
    }

    public List getFaccaos() {
        loadFaccaos();
        return faccaos;
    }

    public void setFaccaos(List faccaos) {
        this.faccaos = faccaos;
    }

    public List getFilteredFaccaos() {
        return filteredFaccaos;
    }

    public void setFilteredFaccaos(List filteredFaccaos) {
        this.filteredFaccaos = filteredFaccaos;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public Suprimento getSuprimento() {
        return suprimento;
    }

    public void setSuprimento(Suprimento suprimento) {
        this.suprimento = suprimento;
    }

    private void loadFaccaos() {
        faccaos = getFaccaoDAO().findAll();
    }

    private void resetFaccao() {
        faccao = new Faccao();
        faccao.setFaccionista(new Faccionista());
    }

    public String addModelo() {
        System.out.println(peca.getQuantidade().toString());
        System.out.println(peca.getModelo().getNome());
        peca.setModelo(getModeloDAO().getById(peca.getModelo().getIdmodelo().intValue()));
        getPecaDAO().persist(peca);
        faccao.getPecas().add(peca);
        peca = new Peca();
        peca.setModelo(new Modelo());
        return "faccao-new";
    }

    public String addAviamento() {
        suprimento.setAviamento(getAviamentoDAO().getById(suprimento.getAviamento().getIdaviamento().intValue()));
        getSuprimentoDAO().persist(suprimento);
        faccao.getSuprimentos().add(suprimento);
        suprimento = new Suprimento();
        suprimento.setAviamento(new Aviamento());
        return "faccao-new";
    }

    public String create() {
        try {
            getFaccaoDAO().persist(faccao);
            displayInfoMessageToUser("Faccao cadastrada com sucesso!");
            loadFaccaos();
            resetFaccao();
            return "faccao-list";
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao cadastrar a nova faccao!");
            System.out.println(e);
            return "faccao-new";
        }
    }

    public String newFaccao() {
        resetFaccao();
        peca = new Peca();
        peca.setModelo(new Modelo());
        suprimento = new Suprimento();
        suprimento.setAviamento(new Aviamento());
        return "faccao-new";
    }

    public List findAll() {
        loadFaccaos();
        return faccaos;
    }

    public String remove() {
        try {
            System.out.println("entrou");
            getFaccaoDAO().remove(selectedFaccao);
            displayInfoMessageToUser("Faccao exclu\355da com sucesso!");
            loadFaccaos();
            resetFaccao();
        } catch (Exception e) {
            displayErrorMessageToUser("Erro ao excluir faccao!");
            System.out.println(e);
        }
        return "faccao-list";
    }

    public List getProximasColetas() {
        
        this.proximasColetas = getFaccaoDAO().findProximasColetas();
        
        return this.proximasColetas;
    }

}
