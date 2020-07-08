/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localSessionBeans;

import entity.Provider;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dell
 */
@Local
public interface ProviderFacadeLocal {

    void create(Provider provider);

    void edit(Provider provider);

    void remove(Provider provider);

    Provider find(Object id);

    List<Provider> findAll();

    List<Provider> findRange(int[] range);

    int count();
    
     
    // get the max primary key
    Integer FindmaxId();
    
    public int getProviderID(String username, String password);
   
    public boolean isProviderExist(String username);
   
    public boolean isValideProvider(String username, String password);

    public List<Provider> findByUsername(String username);
    
    public List<Provider> findById(Integer id);
}
