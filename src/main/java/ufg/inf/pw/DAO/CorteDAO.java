
package ufg.inf.pw.DAO;

import java.util.List;
import javax.persistence.*;
import ufg.inf.pw.model.Corte;

public class CorteDAO
{

    public CorteDAO()
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

    public Corte getById(int id)
    {
        return (Corte)entityManager.find(ufg.inf.pw.model.Corte.class, Integer.valueOf(id));
    }

    public int getNextId()
    {
        Integer id = (Integer)entityManager.createNativeQuery("SELECT idcorte FROM corte f ORDER BY f.idcorte DESC LIMIT 1").getSingleResult();
        return id.intValue() + 1;
    }

    public List findAll()
    {
        return entityManager.createQuery((new StringBuilder()).append("FROM ").append(ufg.inf.pw.model.Corte.class.getName()).append(" f ORDER BY f.idcorte").toString()).getResultList();
    }

    public void persist(Corte corte)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(corte);
            entityManager.getTransaction().commit();
            entityManager.refresh(corte);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Corte corte)
    {
        try
        {
            entityManager.getTransaction().begin();
            Corte persisted = getById(corte.getIdcorte().intValue());
            persisted.setNome(corte.getNome());
            persisted.setFoto(corte.getFoto());
            persisted.setModelo(corte.getModelo());
            entityManager.merge(persisted);
            entityManager.getTransaction().commit();
            entityManager.refresh(corte);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Corte corte)
    {
        try
        {
            entityManager.getTransaction().begin();
            corte = (Corte)entityManager.find(ufg.inf.pw.model.Corte.class, corte.getIdcorte());
            entityManager.remove(corte);
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
            Corte corte = getById(id);
            remove(corte);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    protected EntityManager entityManager;
}
