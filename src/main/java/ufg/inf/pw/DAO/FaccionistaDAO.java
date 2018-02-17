

package ufg.inf.pw.DAO;

import java.util.List;
import javax.persistence.*;
import ufg.inf.pw.model.Faccionista;

public class FaccionistaDAO
{

    public FaccionistaDAO()
    {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager()
    {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("sysconfPU");
        if(entityManager == null)
            entityManager = factory.createEntityManager();
        return entityManager;
    }

    public Faccionista getById(int id)
    {
        return (Faccionista)entityManager.find(ufg.inf.pw.model.Faccionista.class, Integer.valueOf(id));
    }

    public int getNextId()
    {
        Integer id = (Integer)entityManager.createNativeQuery("SELECT idfaccionista FROM faccionista f ORDER BY f.idfaccionista DESC LIMIT 1").getSingleResult();
        return id.intValue() + 1;
    }

    public List findAll()
    {
        return entityManager.createQuery((new StringBuilder()).append("FROM ").append(ufg.inf.pw.model.Faccionista.class.getName()).append(" f ORDER BY f.nome").toString()).getResultList();
    }

    public void persist(Faccionista faccionista)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(faccionista);
            entityManager.getTransaction().commit();
            entityManager.refresh(faccionista);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Faccionista faccionista)
    {
        try
        {
            entityManager.getTransaction().begin();
            Faccionista persisted = getById(faccionista.getIdfaccionista().intValue());
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
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Faccionista faccionista)
    {
        try
        {
            entityManager.getTransaction().begin();
            faccionista = (Faccionista)entityManager.find(ufg.inf.pw.model.Faccionista.class, faccionista.getIdfaccionista());
            entityManager.remove(faccionista);
            entityManager.getTransaction().commit();
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(int id)
    {
        try
        {
            Faccionista faccionista = getById(id);
            remove(faccionista);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    protected EntityManager entityManager;
}
