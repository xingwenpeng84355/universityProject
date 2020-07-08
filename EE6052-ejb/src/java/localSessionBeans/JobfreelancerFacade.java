/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localSessionBeans;

import entity.Freelancer;
import entity.Jobdescription;
import entity.Jobfreelancer;
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
public class JobfreelancerFacade extends AbstractFacade<Jobfreelancer> implements JobfreelancerFacadeLocal {

    @PersistenceContext(unitName = "EE6052-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JobfreelancerFacade() {
        super(Jobfreelancer.class);
    }
    
    public Jobfreelancer findByJobIdAndFreelancerId(Integer jobId,Integer freelancerId){ 
        Query query = em.createNamedQuery("Jobfreelancer.findByJobIdAndFreelancerId");
        query.setParameter("jobId", jobId);
        query.setParameter("freelancerId", freelancerId);
        List temp = query.getResultList();
        if (temp .isEmpty())
               return null; 
        return (Jobfreelancer)temp.get(0);
    }

    public List<Jobfreelancer> findByJobId(Integer id){ 
        Query query = em.createNamedQuery("Jobfreelancer.findByJobId");
        query.setParameter("jobId", id);
        return query.getResultList();
    }
    public List<Jobfreelancer> findByFreelancerId(Integer id){ 
        Query query = em.createNamedQuery("Jobfreelancer.findByFreelancerId");
        query.setParameter("freelancerId", id);
        return query.getResultList();
    }
    
    @Override
    public Integer FindmaxId(){
       Query query=em.createNamedQuery("Jobfreelancer.findMaxId");
       return Integer.parseInt(query.getSingleResult().toString());
    }
    
}
