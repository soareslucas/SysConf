
package ufg.inf.pw.DAO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import ufg.inf.pw.model.Faccao;

public class FaccaoDAO
{

    public FaccaoDAO()
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

    public Faccao getById(int id)
    {
        return (Faccao)entityManager.find(ufg.inf.pw.model.Faccao.class, Integer.valueOf(id));
    }

    public int getNextId()
    {
        Integer id = (Integer)entityManager.createNativeQuery("SELECT idfaccao FROM faccao f ORDER BY f.idfaccao DESC LIMIT 1").getSingleResult();
        return id.intValue() + 1;
    }

    public List findAll()
    {
        return entityManager.createQuery((new StringBuilder()).append("FROM ").append(ufg.inf.pw.model.Faccao.class.getName()).append(" f ORDER BY f.idfaccao").toString()).getResultList();
    }

    public List findProximasColetas()
    {
        Date dt = new Date();
        SimpleDateFormat formatter5 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String data = formatter5.format(dt);
        return entityManager.createQuery((new StringBuilder()).append("FROM ").append(ufg.inf.pw.model.Faccao.class.getName()).append(" f where f.dataColeta > '").append(data).append("' ORDER BY f.idfaccao").toString()).getResultList();
    }

    public void persist(Faccao faccao)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(faccao);
            entityManager.getTransaction().commit();
            entityManager.refresh(faccao);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(Faccao faccao)
    {
        try
        {
            entityManager.getTransaction().begin();
            Faccao persisted = getById(faccao.getIdfaccao().intValue());
            persisted.setDataColeta(faccao.getDataColeta());
            persisted.setDataEntrega(faccao.getDataEntrega());
            persisted.setFaccionista(faccao.getFaccionista());
            persisted.setPecas(faccao.getPecas());
            persisted.setSuprimentos(faccao.getSuprimentos());
            entityManager.merge(persisted);
            entityManager.getTransaction().commit();
            entityManager.refresh(faccao);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Faccao faccao)
    {
        try
        {
            entityManager.getTransaction().begin();
            faccao = (Faccao)entityManager.find(ufg.inf.pw.model.Faccao.class, faccao.getIdfaccao());
            entityManager.remove(faccao);
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
            Faccao faccao = getById(id);
            remove(faccao);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    protected EntityManager entityManager;
}
