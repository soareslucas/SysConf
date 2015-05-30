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
import ufg.inf.pw.model.Faccionista;

/**
 *
 * @author Lucas
 */
public class FaccionistaDAO {

    protected EntityManager entityManager;

    public FaccionistaDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("ConfeccaoPU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public Faccionista getById(final int id) {
        return entityManager.find(Faccionista.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Faccionista> findAll() {
        return entityManager.createQuery("FROM " + Faccionista.class.getName() + " f ORDER BY f.nome")
                .getResultList();
    }

    public void persist(Faccionista faccionista) {
        try {
            
            System.out.println(faccionista.getEndereco());
            
            entityManager.getTransaction().begin();
            entityManager.persist(faccionista);
            entityManager.getTransaction().commit();
            entityManager.refresh(faccionista);
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Faccionista faccionista) {
        try {
            entityManager.getTransaction().begin();
            Faccionista persisted = getById(faccionista.getIdfaccionista());
            persisted.setNome(faccionista.getNome());
            persisted.setDataNascimento(faccionista.getDataNascimento());
            persisted.setEndereco(faccionista.getEndereco());
            persisted.setCidade(faccionista.getCidade());
            persisted.setCep(faccionista.getCep());
            persisted.setTelefone(faccionista.getTelefone());
            persisted.setCelular(faccionista.getCelular());
            persisted.setEmail(faccionista.getEmail());
            entityManager.merge(persisted);
            entityManager.getTransaction().commit();
            entityManager.refresh(faccionista);
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Faccionista faccionista) {
        try {
            entityManager.getTransaction().begin();
            faccionista = entityManager.find(Faccionista.class, faccionista.getIdfaccionista());
            entityManager.remove(faccionista);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(final int id) {
        try {
            Faccionista faccionista = getById(id);
            remove(faccionista);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
