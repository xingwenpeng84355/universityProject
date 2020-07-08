/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localSessionBeans;

import entity.Admin;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dell
 */
@Local
public interface AdminFacadeLocal {

    void create(Admin admin);

    void edit(Admin admin);

    void remove(Admin admin);

    Admin find(Object id);

    List<Admin> findAll();

    List<Admin> findRange(int[] range);

    int count();
     
    //self-added
    public List<Admin> findByUsername(String username);
     
    public int getAdminID(String username, String password);

    public boolean isAdministerExist(String username);
    
    public boolean isValideAdmin(String username, String password);
}
