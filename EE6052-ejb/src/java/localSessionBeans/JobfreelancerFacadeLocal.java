/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localSessionBeans;

import entity.Freelancer;
import entity.Jobdescription;
import entity.Jobfreelancer;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dell
 */
@Local
public interface JobfreelancerFacadeLocal {

    void create(Jobfreelancer jobfreelancer);

    void edit(Jobfreelancer jobfreelancer);

    void remove(Jobfreelancer jobfreelancer);

    Jobfreelancer find(Object id);

    List<Jobfreelancer> findAll();

    List<Jobfreelancer> findRange(int[] range);

    int count();
    
    List<Jobfreelancer> findByFreelancerId(Integer id);
    
    public Integer FindmaxId();
    
    Jobfreelancer findByJobIdAndFreelancerId(Integer jobId,Integer FreelancerId);
    
    List<Jobfreelancer> findByJobId(Integer id);

}
