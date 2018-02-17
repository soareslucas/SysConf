
package ufg.inf.pw.DAO;

import java.util.List;
import javax.persistence.*;
import ufg.inf.pw.model.Peca;

public class PecaDAO
{

    public PecaDAO()
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

    public Peca getById(int id)
    {
        return (Peca)entityManager.find(ufg.inf.pw.model.Peca.class, Integer.valueOf(id));
    }

    public int getNextId()
    {
        Integer id = (Integer)entityManager.createNativeQuery("SELECT idpeca FROM peca f ORDER BY f.idpeca DESC LIMIT 1").getSingleResult();
        return id.intValue() + 1;
    }

    public List findAll()
    {
        return entityManager.createQuery((new StringBuilder()).append("FROM ").append(ufg.inf.pw.model.Peca.class.getName()).append(" f ORDER BY f.idpeca").toString()).getResultList();
    }

    public void persist(Peca peca)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(peca);
            entityManager.getTransaction().commit();
            entityManager.refresh(peca);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Peca peca)
    {
        try
        {
            entityManager.getTransaction().begin();
            peca = (Peca)entityManager.find(ufg.inf.pw.model.Peca.class, peca.getIdpeca());
            entityManager.remove(peca);
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
            Peca peca = getById(id);
            remove(peca);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    protected EntityManager entityManager;
}
