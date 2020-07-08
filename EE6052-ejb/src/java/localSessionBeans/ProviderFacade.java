/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localSessionBeans;

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
public class ProviderFacade extends AbstractFacade<Provider> implements ProviderFacadeLocal {

    @PersistenceContext(unitName = "EE6052-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProviderFacade() {
        super(Provider.class);
    }
    
    @Override
    public Integer FindmaxId(){
       Query query=em.createNamedQuery("Provider.findMaxId");
       return Integer.parseInt(query.getSingleResult().toString());
    }
    @Override
    public int getProviderID(String username, String password) { 
        //create Query
        Query query = em.createNamedQuery("Provider.getProviderID");
        query.setParameter("username", username);
        query.setParameter("password", password);
        //Execute Query
        return (int) query.getSingleResult(); 
    }
  @Override
    public boolean isProviderExist(String username) {
        Query query = em.createNamedQuery("Provider.findByUsername").setParameter("username", username);
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
    public boolean isValideProvider(String username, String password) {
        // create named query and set parameter
        Query query = em.createNamedQuery("Provider.validate");
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
    
    @Override
    public List<Provider> findByUsername(String username){
        Query query = em.createNamedQuery("Provider.findByUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
    
    @Override
    public List<Provider> findById(Integer id){ 
        Query query = em.createNamedQuery("Provider.findById");
        query.setParameter("id", id);
        return query.getResultList(); 
    }
}
