/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localSessionBeans;

import entity.Jobdescription;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dell
 */
@Stateless
public class JobdescriptionFacade extends AbstractFacade<Jobdescription> implements JobdescriptionFacadeLocal {

    @PersistenceContext(unitName = "EE6052-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JobdescriptionFacade() {
        super(Jobdescription.class);
    }
    
    @Override
    public Integer FindmaxId(){
       Query query=em.createNamedQuery("Jobdescription.findMaxId");
       return Integer.parseInt(query.getSingleResult().toString());
    }
    
    @Override
    public List<Jobdescription > findByKeywords(String keywords){
        Query query = em.createNamedQuery("Jobdescription.findByKeywords");
        query.setParameter("keywords", keywords);
        return query.getResultList();
    }
    
    @Override
    public List<Jobdescription> findByProviderID(int ProviderID){ 
        Query query = em.createNamedQuery("Jobdescription.findByIdProvider");
        query.setParameter("idProvider", ProviderID);
        return query.getResultList();
    }
 
    @Override
    public List<Jobdescription > findById(int id){
        Query query = em.createNamedQuery("Jobdescription.findById");
        query.setParameter("id", id);
        return query.getResultList();
    }
}
