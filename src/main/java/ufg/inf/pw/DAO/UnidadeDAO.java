
package ufg.inf.pw.DAO;

import java.util.List;
import javax.persistence.*;
import ufg.inf.pw.model.Unidade;

public class UnidadeDAO
{

    public UnidadeDAO()
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

    public Unidade getById(int id)
    {
        return (Unidade)entityManager.find(ufg.inf.pw.model.Unidade.class, Integer.valueOf(id));
    }

    public List findAll()
    {
        return entityManager.createQuery((new StringBuilder()).append("FROM ").append(ufg.inf.pw.model.Unidade.class.getName()).append(" f ORDER BY f.idunidade").toString()).getResultList();
    }

    public void persist(Unidade unidade)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(unidade);
            entityManager.getTransaction().commit();
            entityManager.refresh(unidade);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Unidade unidade)
    {
        try
        {
            entityManager.getTransaction().begin();
            Unidade persisted = getById(unidade.getIdunidade());
            persisted.setNome(unidade.getNome());
            entityManager.merge(persisted);
            entityManager.getTransaction().commit();
            entityManager.refresh(unidade);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Unidade unidade)
    {
        try
        {
            entityManager.getTransaction().begin();
            unidade = (Unidade)entityManager.find(ufg.inf.pw.model.Unidade.class, Integer.valueOf(unidade.getIdunidade()));
            entityManager.remove(unidade);
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
            Unidade unidade = getById(id);
            remove(unidade);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    protected EntityManager entityManager;
}
