package ufg.inf.pw.DAO;

import java.sql.*;
import java.util.*;
import javax.persistence.*;
import ufg.inf.pw.model.Corte;
import ufg.inf.pw.model.Modelo;
import ufg.inf.pw.utils.JdbcConnection;

public class ModeloDAO {

    public ModeloDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("sysconfPU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public Modelo getById(int id) {
        return (Modelo) entityManager.find(ufg.inf.pw.model.Modelo.class, Integer.valueOf(id));
    }

    public int getNextId() {
        Integer id = (Integer) entityManager.createNativeQuery("SELECT idmodelo FROM modelo f ORDER BY f.idmodelo DESC LIMIT 1").getSingleResult();
        return id.intValue() + 1;
    }

    public List findAllJDBC()
            throws SQLException {
        List listaModelos;
        String query;
        listaModelos = new ArrayList();
        Statement stmt;
        query = "SELECT * FROM modelo f ORDER BY f.idmodelo";
        Connection con = JdbcConnection.getConnection();
        stmt = con.createStatement();
        Modelo modelo;
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            modelo = new Modelo();
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setFoto(rs.getString("foto"));
            modelo.setNome(rs.getString("nome"));
            modelo.setIdmodelo(Integer.valueOf(rs.getInt("idmodelo")));
            listaModelos.add(modelo);
        }
        Iterator i = listaModelos.iterator();
        while (i.hasNext()) {
            Modelo mod = (Modelo) i.next();
            query = (new StringBuilder()).append("SELECT * FROM corte c WHERE c.modelo_idmodelo = ").append(mod.getIdmodelo()).append(" ORDER BY c.idcorte").toString();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Corte corte = new Corte();
                corte.setIdcorte(Integer.valueOf(result.getInt("idcorte")));
                corte.setNome(result.getString("nome"));
                corte.setModelo((Modelo) listaModelos.listIterator().next());
                corte.setFoto(result.getString("foto"));
                mod.getCortes().add(corte);
            }
        }

        con.close();

        return listaModelos;
    }

    public List findAll() {
        return entityManager.createQuery((new StringBuilder()).append("FROM ").append(ufg.inf.pw.model.Modelo.class.getName()).append(" f ORDER BY f.idmodelo").toString()).getResultList();
    }

    public void persist(Modelo modelo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(modelo);
            entityManager.getTransaction().commit();
            entityManager.refresh(modelo);
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Modelo modelo) {
        try {
            entityManager.getTransaction().begin();
            Modelo persisted = getById(modelo.getIdmodelo().intValue());
            persisted.setNome(modelo.getNome());
            persisted.setDescricao(modelo.getDescricao());
            persisted.setFoto(modelo.getFoto());
            entityManager.merge(persisted);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Modelo modelo) {
        try {
            entityManager.getTransaction().begin();
            modelo = (Modelo) entityManager.find(ufg.inf.pw.model.Modelo.class, modelo.getIdmodelo());
            entityManager.remove(modelo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(int id) {
        try {
            Modelo modelo = getById(id);
            remove(modelo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected EntityManager entityManager;
}
