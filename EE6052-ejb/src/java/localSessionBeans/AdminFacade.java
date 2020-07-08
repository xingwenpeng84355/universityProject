/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localSessionBeans;

import entity.Admin;
import entity.Freelancer;
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
public class AdminFacade extends AbstractFacade<Admin> implements AdminFacadeLocal {

    @PersistenceContext(unitName = "EE6052-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminFacade() {
        super(Admin.class);
    }
    
    @Override
    public int getAdminID(String username, String password) { 
        //create Query
        Query query = em.createNamedQuery("Admin.getAdminID");
        query.setParameter("username", username);
        query.setParameter("password", password);
        //Execute Query
        return (int) query.getSingleResult(); 
    }

    @Override
    public boolean isAdministerExist(String username) {
        Query query = em.createNamedQuery("Admin.findByUsername").setParameter("username", username);
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
    public boolean isValideAdmin(String username, String password) {
        // create named query and set parameter
        Query query = em.createNamedQuery("Admin.validate");
        query.setParameter("username", username);
        query.setParameter("password", password);
        
        // return the number of records
        if((long)query.getSingleResult() > 0)
        {
          System.out.println("ATTENTION : Administer " + username + "was queried");
          return true; // successful to login
        }
        //fail to login
        System.out.println("ATTENTION: Someone try to use name"+  username + "have a query");
        return false;
    }
    
    @Override
    public List<Admin> findByUsername(String username){
        Query query = em.createNamedQuery("Admin.findByUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
    
}
