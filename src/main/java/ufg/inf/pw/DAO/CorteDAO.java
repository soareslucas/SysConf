/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufg.inf.pw.DAO;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ufg.inf.pw.beans.ModeloBean;
import ufg.inf.pw.model.Corte;

/**
 *
 * @author Lucas
 */
public class CorteDAO {

    protected EntityManager entityManager;

    public CorteDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("sysconfPU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public Corte getById(final int id) {
        return entityManager.find(Corte.class, id);
    }

    public int getNextId() {
        Integer id = (Integer) entityManager.createNativeQuery("SELECT idcorte FROM corte f ORDER BY f.idcorte DESC LIMIT 1").getSingleResult();
        return id + 1;
    }

    @SuppressWarnings("unchecked")
    public List<Corte> findAll() {
        return entityManager.createQuery("FROM " + Corte.class.getName() + " f ORDER BY f.idcorte")
                .getResultList();
    }

    public void persist(Corte corte) {
        try {

            entityManager.getTransaction().begin();
            entityManager.persist(corte);
            entityManager.getTransaction().commit();
            entityManager.refresh(corte);

            
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Corte corte) {
        try {
            entityManager.getTransaction().begin();
            Corte persisted = getById(corte.getIdcorte());
            persisted.setNome(corte.getNome());
            persisted.setFoto(corte.getFoto());
            persisted.setModelo(corte.getModelo());

            entityManager.merge(persisted);
            entityManager.getTransaction().commit();
            entityManager.refresh(corte);
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Corte corte) {
        try {
            entityManager.getTransaction().begin();
            corte = entityManager.find(Corte.class, corte.getIdcorte());
            entityManager.remove(corte);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(final int id) {
        try {
            Corte corte = getById(id);
            remove(corte);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
