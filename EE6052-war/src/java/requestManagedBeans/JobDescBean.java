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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
import localSessionBeans.JobdescriptionFacadeLocal;
import localSessionBeans.JobfreelancerFacadeLocal;
import localSessionBeans.ProviderFacadeLocal;
import sessionManagedBeans.LoginManagedBean;

/**
 *
 * @author dell
 */
@Named(value = "jobDescBean")
@RequestScoped
public class JobDescBean implements Serializable {
 
    @EJB
    JobdescriptionFacadeLocal jdEJB;
    
    @EJB
    ProviderFacadeLocal proEJB;
    
    @EJB
    JobfreelancerFacadeLocal jdlcEJB;
    
    @Resource(mappedName = "mydes")
    private Queue dest;
    
    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext jmsCtx;
    
    public JobDescBean() { 
        
        FacesContext facesContext = FacesContext.getCurrentInstance();  
        LoginManagedBean loginManagedBean = (LoginManagedBean) facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "loginBean");  
        String loginType = loginManagedBean.getLoginType();
        if (loginType.equals("admin")){
            //this.idProvider = 
        } else if (loginType.equals("provider")){ 
           this.idProvider = loginManagedBean.getProvider().getId();
        } else if (loginType.equals("freelancer")){ 
           // this.idProvider = 
        } else { // nothing has logined.
            
        }
    }
    
    private Integer id=0;
    private String  title; 
    private String  keywords; 
    private String  description; 
    private Integer payment; 
    private String  state; 
    private Integer idProvider;

    private List<Jobdescription> jd_all;
    
    public boolean hasSentOfferRequest(Integer jobId, Integer freelancerId){
        Jobfreelancer relation = jdlcEJB.findByJobIdAndFreelancerId(jobId,freelancerId);
        if (relation == null)    
            return false;    
        else 
            return true;
    }
    
    public void sendJobRequest(Integer jobId, Integer freelancerId){
        Jobfreelancer jobfreelancer = new Jobfreelancer();
        jobfreelancer.setFreelancerId(freelancerId);
        jobfreelancer.setJobId(jobId);
        jobfreelancer.setId(jdlcEJB.FindmaxId()+1); 
        //setPadding and setGetoffer => false
        jdlcEJB.create(jobfreelancer);
    }  
    
    public void revokeJobRequest(Integer jobId, Integer freelancerId){
        Jobfreelancer relation = jdlcEJB.findByJobIdAndFreelancerId(jobId,freelancerId);
        jdlcEJB.remove(relation);
    }
    
    public boolean shouldDisplaySentJobRequest(Integer jobId, Integer freelancerId){
        return !this.hasSentOfferRequest(jobId, freelancerId)&& !isOfferPadding(jobId, freelancerId) && isOfferPaddingIsNull(jobId, freelancerId);
    }
    public boolean shouldDisplaRevokeOffer(Integer jobId, Integer freelancerId){  
        return this.hasSentOfferRequest(jobId, freelancerId)&&!isOfferPadding(jobId, freelancerId) ;
    } 
    public boolean shouldDisplayAcceptOffer(Integer jobId, Integer freelancerId){  
        boolean isPadding = isOfferPadding(jobId, freelancerId);
        boolean isOfferRequestSent = this.hasSentOfferRequest(jobId, freelancerId);
        boolean isOfferPaddingIsNull_ = isOfferPaddingIsNull (jobId, freelancerId); 
        return  !isOfferPaddingIsNull_ && isOfferRequestSent && isPadding ;
    }
    
    public boolean hasOfferAccepted(Integer jobId, Integer freelancerId){
        Jobfreelancer relation = jdlcEJB.findByJobIdAndFreelancerId(jobId,freelancerId);
        if (relation == null)    
            return false;    
        else if ( null == relation.getOfferaccept() )
            return false;
        else
            return relation.getOfferaccept(); 
    }
     
    public boolean isOfferPaddingIsNull(Integer jobId, Integer freelancerId){
        Jobfreelancer relation = jdlcEJB.findByJobIdAndFreelancerId(jobId,freelancerId);
        if (relation == null)    
            return true;    
        else 
            return false; 
    }
    static int c=0;
    public boolean isOfferPadding(Integer jobId, Integer freelancerId){
        Jobfreelancer relation = jdlcEJB.findByJobIdAndFreelancerId(jobId,freelancerId);
        if (relation == null){
            System.err.println("jobId:"+jobId+", freelancerId"+freelancerId);
            return false;
        }    
        else if ( null == relation.getOfferpadding()){
            return false;
        } else {  
            return relation.getOfferpadding(); 
        }
    }
      

    private void sendJMSMessageToDest(String messageData) {
        jmsCtx.createProducer().send(dest, messageData);
    }

    public void acceptJob(Jobdescription job,Integer freelancerId){
        // TODO: send job requst to DB -> update table jobfreelancer.
        int jobId = job.getId();
        Jobfreelancer relation = jdlcEJB.findByJobIdAndFreelancerId(jobId,freelancerId);
        relation.setOfferpadding(false);
        relation.setOfferaccept(true);
        jdlcEJB.edit(relation); 
        
        List<Jobdescription> templist = jdEJB.findById(jobId);
        Jobdescription jobdescription = templist.get(0);
        jobdescription.setState("closed");
        jdEJB.edit(jobdescription);
        
        sendJMSMessageToDest("[Job closed now]-Job [id="+jobId+"] has been assigned state of closed, by freelancer[id="+freelancerId+"]");
        System.out.println("[Job closed now]-Job [id="+jobId+"] has been assigned state of closed, by freelancer[id="+freelancerId+"]");
    }  
    
    public List<Jobdescription> findJobdescriptionByID(){
        id = id == null ? -1 : id;
        List<Jobdescription> result = jdEJB.findById(id);
        result = result == null ? new ArrayList<Jobdescription>() : result;
        return result;
    }
    
    public List<Jobdescription> findJobdescriptionAll(){
        jd_all = jdEJB.findAll();
        return jd_all;
    }
    
    public List<Jobdescription> findJobdescriptionByKeywords(){
        return jdEJB.findByKeywords(keywords);
    }
    
    public List<Jobdescription> findJobdescriptionByProviderId(int providerId){
        List<Jobdescription> myJob = new ArrayList<Jobdescription>();
        List<Jobdescription> all = findJobdescriptionAll();
        if (all == null ) 
            return null;
        int le = all.size();
        for (int i=0; i < le; i++) {
            int providerID = all.get(i).getIdProvider();
            if(idProvider == null){}
            else if (idProvider == providerID){
               myJob.add( all.get(i));
            } 
        } 
        return myJob;
    }
    
    public List<Jobdescription> findAllOpenedJobdescription(){
        List<Jobdescription> openedJob = new ArrayList<Jobdescription>();
        List<Jobdescription> all = findJobdescriptionAll();
        if (all == null ) 
            return null;
        int le = all.size();
        for (int i=0; i < le; i++) {
            String jobState = all.get(i).getState();
            if (jobState == null){}
            else if(jobState.equals("open")){
               openedJob.add( all.get(i));
            } 
        } 
        return openedJob;
    }
    
    public List<Jobdescription> findMyOpenedJobdescription(){ 
        List<Jobdescription> openedJob = new ArrayList<Jobdescription>();
       if (null==idProvider) { return openedJob; }
        List<Jobdescription> all = findJobdescriptionByProviderId(idProvider);
        if (all == null ) 
            return null;
        int le = all.size();
        for (int i=0; i < le; i++) {
            String jobState = all.get(i).getState();
            if (jobState == null){}
            else if(jobState.equals("open")){
               openedJob.add( all.get(i));
            } 
        } 
        return openedJob;
    }
    
    public List<Jobdescription> findMyJobdescription(){ 
        List<Jobdescription> emptyJobs = new ArrayList<Jobdescription>();
       if (null==idProvider) { return emptyJobs; }
        List<Jobdescription> myJobs = findJobdescriptionByProviderId(idProvider);
        if (myJobs== null ) 
            return emptyJobs;
        else 
            return myJobs;
    }
   /*  public List<Provider > getProductByID(Integer id) {
         return pro.find(id);
    } */
    public List<Jobdescription > getJobsByKeywords(String keyword) {
         return jdEJB.findByKeywords(keyword);
    }
    
    public List<Jobdescription > getByJobsProviderID(int ProviderID) {
         return jdEJB.findByProviderID(ProviderID);
    }
    
    
    public void deleteJobdescription(Jobdescription p){ 
        List<Jobfreelancer> list = jdlcEJB.findByJobId(p.getId());
        if (list.isEmpty()){}
        else{
            int l = list.size();
            for(int i=0; i < l; i++){
                jdlcEJB.remove(list.get(i));
            }
        }
        jdEJB.remove(p);
    }
    public void completeJob(Jobdescription p){
        p.setState("completed");
        jdEJB.edit(p);
    }
      
    public void addNewJobdescription(JobDescBean jobDescBean, int providerId){ 
        Jobdescription job = new Jobdescription();
        job.setDescription(jobDescBean.getDescription());
        job.setTitle(jobDescBean.getTitle());
        job.setState(jobDescBean.getState());
        job.setKeywords(jobDescBean.getKeywords());
        job.setPayment(jobDescBean.getPayment());
        int maxID = jdEJB.FindmaxId();
        //id = maxID+1;
        job.setId(maxID+1);
        job.setIdProvider(providerId);
        jdEJB.create(job);
    }
    
    
    public void addNewJobdescription(int providerId){//(String productName,Integer typeID,int quantity,float price,int size,String productMSG){
        Jobdescription job = new Jobdescription();
        job.setKeywords(keywords);
        job.setDescription(description);
        job.setTitle(title);
        job.setState("open");
        job.setPayment(payment); 
         
        int maxID = jdEJB.FindmaxId();
        id = maxID+1;
        job.setId(id); 
        job.setIdProvider(idProvider);
        jdEJB.create(job);
        //sendJMSMessageToDest("You hava a new product: " + productName );
    }
 
    public String checkIfAddedIntoDB(){ 
        int temp_id = id == null ? -1 : id;
        List<Jobdescription> pList = jdEJB.findById(temp_id);
        if(null == pList || pList.isEmpty()){
            return " please add new job description record";
        }
        else  {
            return " This job description is in the database.";
        } 
    }
  
    public String getProviderUsername(Jobdescription job_in){ 
        List<Jobdescription> temp = jdEJB.findById(job_in.getId());
        Jobdescription job = temp.get(0);
        int id = job.getIdProvider(); 
        List<Provider> providerList = proEJB.findById(id);
        Provider temp_provider = providerList.get(0); 
        
        return temp_provider.getUsername();
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
     
    public boolean isOfferOpen(Integer jobId){
        List<Jobdescription> temp = jdEJB.findById(jobId);
        if (temp.isEmpty())
            return false;
        
        Jobdescription job = temp.get(0); 
        return "open".equals(job.getState());
    }
}
