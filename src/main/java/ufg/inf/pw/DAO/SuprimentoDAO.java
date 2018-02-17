
package ufg.inf.pw.DAO;

import java.util.List;
import javax.persistence.*;
import ufg.inf.pw.model.Suprimento;

public class SuprimentoDAO
{

    public SuprimentoDAO()
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

    public Suprimento getById(int id)
    {
        return (Suprimento)entityManager.find(ufg.inf.pw.model.Suprimento.class, Integer.valueOf(id));
    }

    public int getNextId()
    {
        Integer id = (Integer)entityManager.createNativeQuery("SELECT idsuprimento FROM suprimento f ORDER BY f.idsuprimento DESC LIMIT 1").getSingleResult();
        return id.intValue() + 1;
    }

    public List findAll()
    {
        return entityManager.createQuery((new StringBuilder()).append("FROM ").append(ufg.inf.pw.model.Suprimento.class.getName()).append(" f ORDER BY f.idsuprimento").toString()).getResultList();
    }

    public void persist(Suprimento suprimento)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(suprimento);
            entityManager.getTransaction().commit();
            entityManager.refresh(suprimento);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Suprimento suprimento)
    {
        try
        {
            entityManager.getTransaction().begin();
            suprimento = (Suprimento)entityManager.find(ufg.inf.pw.model.Suprimento.class, suprimento.getIdsuprimento());
            entityManager.remove(suprimento);
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
            Suprimento suprimento = getById(id);
            remove(suprimento);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    protected EntityManager entityManager;
}
