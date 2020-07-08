/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requestManagedBeans;

import entity.Freelancer;
import entity.Provider;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import localSessionBeans.FreelancerFacadeLocal;
import sessionManagedBeans.LoginManagedBean;

/**
 *
 * @author dell
 */
@Named(value = "freelancerBean")
@RequestScoped
public class FreelancerBean implements Serializable {
 
    @EJB
    FreelancerFacadeLocal flcEJB;
    
    public FreelancerBean() {   
            FacesContext facesContext = FacesContext.getCurrentInstance();  
            LoginManagedBean loginManagedBean = (LoginManagedBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "loginBean");  
            if(loginManagedBean.getLoginType().equals("freelancer")){ 
                Freelancer this_ = loginManagedBean.getFreelancer();
                this.id = this_.getId();
                this.username = this_.getUsername();
                this.idJobIntent = this_.getIdJobIntent()  ;
                this.payment = this_.getPayment()  ;
                this.name = this_.getName();
                this.skill = this_.getSkill();
                this.description = this_.getDescription();
                this.password = this_.getPassword();

                this.hide_payment = true;
            }
    }
    
    
    private String username;
    private String password;
    private String name;
    private String skill;
    private String description;
    private Integer id ; 
    private Integer idJobIntent; 
    private Integer payment;

    private List<Freelancer> pro_all;
	
    private boolean hide_payment;
    
    public List<Freelancer> findFreelancerById(){
       return flcEJB.findById(id);
    }
    
    public List<Freelancer> findFreelancerAll(){
        pro_all = flcEJB.findAll();
        return pro_all;
    }
   /*
     public List<Provider > getProductByID(Integer id) {
         return pro.find(id);
    }
    
     */
    public List<Freelancer> getFreelancerByUsername(String username) {
         return flcEJB.findByUsername(username);
    }
    
    
    public void deleteFreelancer(Freelancer p){
        flcEJB.remove(p);
    }
     
    public boolean isHidden(){return this.hide_payment;}
    public void setHidden(boolean b){this.hide_payment = b;}
    
    public void addNewFreelancer(){//(String productName,Integer typeID,int quantity,float price,int size,String productMSG){
        Freelancer jobHunter = new Freelancer();
        
        jobHunter.setUsername(username);
        jobHunter.setPassword(password);
        jobHunter.setName(name);
        jobHunter.setSkill(skill);
        jobHunter.setDescription(description);
        jobHunter.setPayment(0);
        int maxID = flcEJB.FindmaxId();
        id = maxID+1;
        jobHunter.setId(id); 
         
        
        List<Freelancer> pList = flcEJB.findByUsername(username);
        if(pList.isEmpty()){ 
             flcEJB.create(jobHunter); 
        } else {
            System.err.println("This username has exist on the DB!");
        } 
        //sendJMSMessageToDest("You hava a new product: " + productName );
    }
    
    
    public void updateInfo(){//(String productName,Integer typeID,int quantity,float price,int size,String productMSG){
        Freelancer jobHunter = new Freelancer();
        
        jobHunter.setId(id); 
        jobHunter.setPassword(password);
        jobHunter.setName(name);
        jobHunter.setSkill(skill);
        jobHunter.setPayment(payment);
        jobHunter.setDescription(description); 
        jobHunter.setIdJobIntent(idJobIntent) ;
                
         List<Freelancer> res = flcEJB.findByUsername(username);
         if ( res.isEmpty() ){
            jobHunter.setUsername(username);
            flcEJB.edit(jobHunter);
         } else {
             System.err.println("Can't use this username:"+  username +" because it have already exist");
             List<Freelancer> freelancer_with_id = flcEJB.findById(id);
             Freelancer oldFreelancer = freelancer_with_id.get(0);
             int original_id = oldFreelancer.getId();
             jobHunter.setId(original_id);
             jobHunter.setUsername(username);
             flcEJB.remove(oldFreelancer);
             flcEJB.create(jobHunter);
         } 
        token_count++;
        //sendJMSMessageToDest("You hava a new product: " + productName );
    }
 
    public String checkIfAddedIntoDB(){
        if(username == null || "".equals(username))
            return "";
        List<Freelancer> pList = flcEJB.findByUsername(username);
        if(pList.isEmpty()){
            return " is not in database";
        }
        else  {
            return " This username is in the database, please click 'back' for go back.";
        } 
    }
 
    private int token_count = 0;
    
    public String checkIfUpdated(){ 
        List<Freelancer> pList = flcEJB.findByUsername(username);
        if(pList.isEmpty()){ 
             System.err.println("This freelancer "+ username+"no exist on the DB!");
        } else {
            Freelancer jh = pList.get(0);
            if (token_count != 0 &&
                    this.password.equals(jh.getPassword()) && this.name.equals(jh.getName()) &&
                    this.description.equals(jh.getDescription()) &&
                    this.skill.equals(jh.getSkill()) ){
                return "It has been updated!";
            } 
        }  
        return "Not been update..."; 
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdJobIntent() {
        return idJobIntent;
    }

    public void setIdJobIntent(Integer idJobIntent) {
        this.idJobIntent = idJobIntent;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }
    
    
    
}
