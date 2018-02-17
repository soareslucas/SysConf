
package ufg.inf.pw.DAO;

import java.util.List;
import javax.persistence.*;
import ufg.inf.pw.model.Aviamento;

public class AviamentoDAO
{

    public AviamentoDAO()
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

    public Aviamento getById(int id)
    {
        return (Aviamento)entityManager.find(ufg.inf.pw.model.Aviamento.class, Integer.valueOf(id));
    }

    public int getNextId()
    {
        Integer id = (Integer) entityManager.createNativeQuery("SELECT idaviamento FROM aviamento f ORDER BY f.idaviamento DESC LIMIT 1").getSingleResult();
        return id.intValue() + 1;
    }

    public List findAll()
    {
        return entityManager.createQuery((new StringBuilder()).append("FROM ").append(ufg.inf.pw.model.Aviamento.class.getName()).append(" f ORDER BY f.idaviamento").toString()).getResultList();
    }

    public void persist(Aviamento aviamento)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(aviamento);
            entityManager.getTransaction().commit();
            entityManager.refresh(aviamento);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Aviamento aviamento)
    {
        try
        {
            entityManager.getTransaction().begin();
            Aviamento persisted = getById(aviamento.getIdaviamento().intValue());
            persisted.setNome(aviamento.getNome());
            persisted.setUnidade(aviamento.getUnidade());
            entityManager.merge(persisted);
            entityManager.getTransaction().commit();
            entityManager.refresh(aviamento);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Aviamento aviamento)
    {
        try
        {
            entityManager.getTransaction().begin();
            aviamento = (Aviamento)entityManager.find(ufg.inf.pw.model.Aviamento.class, aviamento.getIdaviamento());
            entityManager.remove(aviamento);
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
            Aviamento aviamento = getById(id);
            remove(aviamento);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    protected EntityManager entityManager;
}
