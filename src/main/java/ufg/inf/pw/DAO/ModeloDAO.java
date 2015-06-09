/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufg.inf.pw.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ufg.inf.pw.model.Modelo;

/**
 *
 * @author Lucas
 */
public class ModeloDAO {

    protected EntityManager entityManager;

    public ModeloDAO() {
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

    public Modelo getById(final int id) {
        return entityManager.find(Modelo.class, id);
    }

    public int getNextId() {
        Integer id = (Integer) entityManager.createNativeQuery("SELECT idmodelo FROM modelo f ORDER BY f.idmodelo DESC LIMIT 1").getSingleResult();
        return id + 1;
    }

    @SuppressWarnings("unchecked")
    public List<Modelo> findAll() {
        return entityManager.createQuery("FROM " + Modelo.class.getName() + " f ORDER BY f.idmodelo")
                .getResultList();
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
            Modelo persisted = getById(modelo.getIdmodelo());
            persisted.setNome(modelo.getNome());
            persisted.setDescricao(modelo.getDescricao());
            persisted.setFoto(modelo.getFoto());

            entityManager.merge(persisted);
            entityManager.getTransaction().commit();
            entityManager.refresh(modelo);
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Modelo modelo) {
        try {
            entityManager.getTransaction().begin();
            modelo = entityManager.find(Modelo.class, modelo.getIdmodelo());
            entityManager.remove(modelo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(final int id) {
        try {
            Modelo modelo = getById(id);
            remove(modelo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
