/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionManagedBeans;

import entity.Admin;
import entity.Freelancer;
import entity.Provider;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import localSessionBeans.AdminFacadeLocal;
import localSessionBeans.FreelancerFacadeLocal;
import localSessionBeans.ProviderFacadeLocal;

/**
 *
 * @author dell
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginManagedBean implements Serializable {

    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
    }
    @EJB
    AdminFacadeLocal adminEJB;
    
    @EJB 
    FreelancerFacadeLocal freelancerEJB;
    
    @EJB 
    ProviderFacadeLocal providerEJB;
     
    private String username;
    private String password;
    
    private Admin admin;
    private Provider provider;
    private Freelancer freelancer;
             
    private String loginType;
    
    public String logout ( ){
        username=password=""; 
        admin = null;
        provider = null;
        freelancer = null;
        return "login";
    }
    
    
    public String login ( ){ 
       boolean valide = adminEJB.isValideAdmin(username, password);
       if (valide){
            List<Admin> admin_ = adminEJB.findByUsername(username);
            if (admin_ == null){System.err.println("admin :"+username+"is null");}
            else if (admin_.isEmpty()){System.err.println("admin :"+username+" not in database");}
            else { admin = admin_.get(0);}
            loginType = "admin";
            return "home_admin";
       }
       
       valide = freelancerEJB.isValideFreelancer(username, password);
       if (valide){
            List<Freelancer> freelancer_ = freelancerEJB.findByUsername(username);
            if (freelancer_ == null){System.err.println("Freelancer :"+username+"is null");}
            else if (freelancer_.isEmpty()){System.err.println("Freelancer :"+username+" not in database");}
            else { freelancer = freelancer_.get(0);}
            loginType = "freelancer";
            return "home_freelancer";
       }
       
       valide = providerEJB.isValideProvider(username, password);
       if (valide){
            List<Provider> provider_ = providerEJB.findByUsername(username);
            if (provider_ == null){System.err.println("Provider :"+username+"is null");}
            else if (provider_.isEmpty()){System.err.println("Provider :"+username+" not in database");}
            else { provider = provider_.get(0);}
            loginType = "provider";
            return "home_provider";
       }
       
       System.out.println("Login Failed! Not Registered!");
       return "login"; 
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin getAdmin() {
        return admin;
    }
    
    public String getLoginType(){return loginType;}
    
    public Provider getProvider() {
        return provider;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

}
