/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localSessionBeans;

import entity.Jobdescription;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dell
 */
@Local
public interface JobdescriptionFacadeLocal {

    void create(Jobdescription jobdescription);

    void edit(Jobdescription jobdescription);

    void remove(Jobdescription jobdescription);

    Jobdescription find(Object id);

    List<Jobdescription> findAll();

    List<Jobdescription> findRange(int[] range);

    int count();
    
    // get the max primary key
    Integer FindmaxId();
    
    public List<Jobdescription > findByKeywords(String keyword);
    
     public List<Jobdescription > findById(int id);

    public List<Jobdescription> findByProviderID(int ProviderID);
 
}
