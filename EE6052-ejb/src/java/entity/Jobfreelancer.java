/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "JOBFREELANCER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jobfreelancer.findMaxId",query = "SELECT MAX(p.id) FROM Jobfreelancer p")
    , @NamedQuery(name = "Jobfreelancer.findAll", query = "SELECT j FROM Jobfreelancer j")
    , @NamedQuery(name = "Jobfreelancer.findByJobIdAndFreelancerId", query = "SELECT j FROM Jobfreelancer j WHERE j.jobId = :jobId AND j.freelancerId = :freelancerId")
    , @NamedQuery(name = "Jobfreelancer.findById", query = "SELECT j FROM Jobfreelancer j WHERE j.id = :id")
    , @NamedQuery(name = "Jobfreelancer.findByJobId", query = "SELECT j FROM Jobfreelancer j WHERE j.jobId = :jobId")
    , @NamedQuery(name = "Jobfreelancer.findByFreelancerId", query = "SELECT j FROM Jobfreelancer j WHERE j.freelancerId = :freelancerId")
    , @NamedQuery(name = "Jobfreelancer.findByOfferpadding", query = "SELECT j FROM Jobfreelancer j WHERE j.offerpadding = :offerpadding")
    , @NamedQuery(name = "Jobfreelancer.findByOfferaccept", query = "SELECT j FROM Jobfreelancer j WHERE j.offeraccept = :offeraccept")})
public class Jobfreelancer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "JOB_ID")
    private int jobId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREELANCER_ID")
    private int freelancerId;
    @Column(name = "OFFERPADDING")
    private Boolean offerpadding;
    @Column(name = "OFFERACCEPT")
    private Boolean offeraccept;

    public Jobfreelancer() {
    }

    public Jobfreelancer(Integer id) {
        this.id = id;
    }

    public Jobfreelancer(Integer id, int jobId, int freelancerId) {
        this.id = id;
        this.jobId = jobId;
        this.freelancerId = freelancerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(int freelancerId) {
        this.freelancerId = freelancerId;
    }

    public Boolean getOfferpadding() {
        return offerpadding;
    }

    public void setOfferpadding(Boolean offerpadding) {
        this.offerpadding = offerpadding;
    }

    public Boolean getOfferaccept() {
        return offeraccept;
    }

    public void setOfferaccept(Boolean offeraccept) {
        this.offeraccept = offeraccept;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jobfreelancer)) {
            return false;
        }
        Jobfreelancer other = (Jobfreelancer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Jobfreelancer[ id=" + id + " ]";
    }
    
}
