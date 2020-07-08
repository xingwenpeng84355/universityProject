/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localSessionBeans;

import entity.Freelancer;
import entity.Provider;
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
public class FreelancerFacade extends AbstractFacade<Freelancer> implements FreelancerFacadeLocal {

    @PersistenceContext(unitName = "EE6052-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FreelancerFacade() {
        super(Freelancer.class);
    }
    
    @Override
    public Integer FindmaxId(){
       Query query=em.createNamedQuery("Freelancer.findMaxId");
       return Integer.parseInt(query.getSingleResult().toString());
    }
    
    @Override
    public List<Freelancer> findByUsername(String username){
        Query query = em.createNamedQuery("Freelancer.findByUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
    
    @Override
    public int getFreelancerID(String username, String password) { 
        //create Query
        Query query = em.createNamedQuery("Freelancer.getFreelancerID");
        query.setParameter("username", username);
        query.setParameter("password", password);
        //Execute Query
        return (int) query.getSingleResult(); 
    }
    
    public List<Freelancer> findById(Integer id){
        Query query = em.createNamedQuery("Freelancer.findById");
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public boolean isFreelancerExist(String username) {
        Query query = em.createNamedQuery("Freelancer.findByUsername").setParameter("username", username);
        // return query result
        if((long)query.getSingleResult() > 0)
        {
         // exist
         return true;
        }
        // not exist
        return false;
    }

    @Override
    public boolean isValideFreelancer(String username, String password) {
        // create named query and set parameter
        Query query = em.createNamedQuery("Freelancer.validate");
        query.setParameter("username", username);
        query.setParameter("password", password);
        
        // return the number of records
        if((long)query.getSingleResult() > 0)
        {
          System.out.println("ATTENTION : Freelancer " + username + "was queried");
          return true; // successful to login
        }
        //fail to login
        System.out.println("ATTENTION: Someone try to use name"+  username + "have a query");
        return false;
    }
}
