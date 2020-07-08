/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requestManagedBeans;

import entity.Freelancer;
import entity.Jobdescription;
import entity.Jobfreelancer;
import entity.Provider;
import localSessionBeans.*;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import sessionManagedBeans.LoginManagedBean;

/**
 *
 * @author dell
 */
@Named(value = "providerBean")
@RequestScoped
public class ProviderBean implements Serializable {
 
    public ProviderBean() {  
            FacesContext facesContext = FacesContext.getCurrentInstance();  
            LoginManagedBean loginManagedBean = (LoginManagedBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "loginBean");  
            if(loginManagedBean.getLoginType().equals("provider")){ 
                Freelancer this_ = loginManagedBean.getFreelancer();
                this.id = this_.getId();
                this.username = this_.getUsername(); 
                this.password = this_.getPassword();
            }
    }
    
    private Integer id;
    private String username;
    private String password;

    @EJB
    ProviderFacadeLocal proEJB;
    
    @EJB
    JobfreelancerFacadeLocal jflcEJB;
    
    @EJB
    FreelancerFacadeLocal flEJB;
    
    @EJB
    JobdescriptionFacadeLocal jobEJB;
    
    private List<Provider> pro_all;
    
    @Resource(mappedName = "mydes")
    private Queue dest;
    
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext jmsCtx;
    
    private void sendJMSMessageToDest(String messageData) {
        jmsCtx.createProducer().send(dest, messageData);
    }
    
    
    
    public boolean shouldShowSendOffer(Integer JobId, Integer FreelancerId){
        List<Jobdescription> list_temp = jobEJB.findById(JobId);
        Jobdescription job = list_temp.get(0);
        boolean isJobOpen = job.getState().equals("open");
        return  isJobOpen;
    }
    public boolean shouldShowCompleteOffer(Integer JobId, Integer FreelancerId){
        List<Jobdescription> list_temp = jobEJB.findById(JobId);
        Jobdescription job = list_temp.get(0);
        boolean isJobClosed = job.getState().equals("closed");
        Jobfreelancer relation = jflcEJB.findByJobIdAndFreelancerId(JobId, FreelancerId);
        boolean isAssignedJob = false;
        if(  relation!= null ){ 
            if(relation.getOfferaccept()==null){
                isAssignedJob = false;
            } else if (relation.getOfferaccept()==true) {
                isAssignedJob = true;
            } else {
                isAssignedJob = false;
            }
        }
        return  isJobClosed && isAssignedJob;
    }
    
    public String sendOffer(Integer jobId, Integer freelancerId){
        //System.err.println(JobId+","+ FreelancerId);
        Jobfreelancer relation = jflcEJB.findByJobIdAndFreelancerId(jobId, freelancerId);
        relation.setOfferpadding(true);
        jflcEJB.edit(relation);
        
        sendJMSMessageToDest("Provider[id="+id+"], has sent a Job [id="+jobId+"] Offer to freelancer[id="+freelancerId+"] -> when the freelancer accept it, the 'close' state of the job will be assigned.");
        
        return "";
    }
    
    public String complete(Jobdescription job, Integer FreelancerId){ 
        job.setState("completed");
        jobEJB.edit(job);
        
        List<Freelancer> list_temp = flEJB.findById(FreelancerId);
        Freelancer freelancer = list_temp.get(0);
        freelancer.setPayment(freelancer.getPayment()+job.getPayment());
        flEJB.edit(freelancer);
        return "";
    }
    
    public boolean isOfferAccepted(Integer JobId, Integer FreelancerId){
        Jobfreelancer relation = jflcEJB.findByJobIdAndFreelancerId(JobId, FreelancerId);
        if (relation==null) return false;
        boolean accepted = relation.getOfferaccept() == null ? false : relation.getOfferaccept() ;
        return accepted;
    }
    
    public boolean isOfferPadding(Integer JobId, Integer FreelancerId){
        Jobfreelancer relation = jflcEJB.findByJobIdAndFreelancerId(JobId, FreelancerId);
        if (relation==null) return false;
        boolean padding = relation.getOfferpadding()== null ? false : relation.getOfferpadding() ;
        return padding;
    }
	
    public List<Provider> findProviderAll(){
        pro_all = proEJB.findAll();
        return pro_all;
    }
    
   /*  public List<Provider > getProductByID(Integer id) {
         return pro.find(id);
    } */
    public List<Freelancer > getInterestedFreelancerByJobId(Integer jobId) {
          List<Jobfreelancer> freelancers = jflcEJB.findByJobId(jobId);
          List<Freelancer > res = new ArrayList<Freelancer>();
          for(Jobfreelancer i: freelancers){
              Integer id = i.getFreelancerId();
              List<Freelancer> fl_ = flEJB.findById(id); // must have one and only one element
               Freelancer fl = fl_.get(0);
               res.add(fl);
          }
          return res;
    }
    
    private Freelancer currentlyCheckedFreelancer;
    private Integer currentlyCheckedJobId;

    public Integer getCurrentlyCheckedJobId() {
        return currentlyCheckedJobId;
    }

    public void setCurrentlyCheckedJobId(Integer currentlyCheckedJobId) {
        this.currentlyCheckedJobId = currentlyCheckedJobId;
    }
    
    public Freelancer getCurrentlyCheckedFreelancer() {
        return currentlyCheckedFreelancer;
    }

    public void setCurrentlyCheckedFreelancer(Freelancer currentlyCheckedFreelancer) {
        this.currentlyCheckedFreelancer = currentlyCheckedFreelancer;
    } 
    public String lookFreelancerProfile(String username, Integer jobId){ 
        List<Freelancer> freelancer_ = flEJB.findByUsername(username);
        currentlyCheckedFreelancer = freelancer_.get(0);
        this.currentlyCheckedJobId = jobId;
        return "freelancerProfile";
    }
    
    
    public List<Provider > getProviderByUsername(String username) {
         return proEJB.findByUsername(username);
    }
    
    
    public void deleteProvider(Provider p){
        proEJB.remove(p);
    }
      
    public void addNewProvider(){//(String productName,Integer typeID,int quantity,float price,int size,String productMSG){
        Provider jobProv = new Provider();
        jobProv.setUsername(username);
        jobProv.setPassword(password);
        int maxID = proEJB.FindmaxId();
        id = maxID+1;
        jobProv.setId(id);
        
         List<Provider> pList = proEJB.findByUsername(username);
        if(pList.isEmpty()){ 
             proEJB.create(jobProv); 
        } else {
            System.err.println("This username has exist on the DB!");
        } 
        //sendJMSMessageToDest("You hava a new product: " + productName );
    }
 
    public String checkIfAddedIntoDB(){
        if(username == null || "".equals(username))
            return "";
        List<Provider> pList = proEJB.findByUsername(username);
        if(pList.isEmpty()){
            return " is not in database";
        }
        else  {
            return " This username is in the database, please click 'back' for go back.";
        } 
    }
    
    public void addNewJob(JobDescBean jobDescBean){//(String productName,Integer typeID,int quantity,float price,int size,String productMSG){
        jobDescBean.addNewJobdescription(jobDescBean,id); 
        //sendJMSMessageToDest("You hava a new product: " + productName );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    
}
