/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localSessionBeans;

import entity.Freelancer;
import entity.Provider;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dell
 */
@Local
public interface FreelancerFacadeLocal {

    void create(Freelancer freelancer);

    void edit(Freelancer freelancer);

    void remove(Freelancer freelancer);

    Freelancer find(Object id);

    List<Freelancer> findAll();

    List<Freelancer> findRange(int[] range);

    int count();
     
    public int getFreelancerID(String username, String password);
    
    public boolean isValideFreelancer(String username, String password);
    
     public boolean isFreelancerExist(String username);
     
    // get the max primary key
    Integer FindmaxId();
    
     public List<Freelancer> findByUsername(String username);
     
     public List<Freelancer> findById(Integer id);
}
