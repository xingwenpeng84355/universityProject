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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "JOBDESCRIPTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jobdescription.findMaxId",query = "SELECT MAX(p.id) FROM Jobdescription p")
    , @NamedQuery(name = "Jobdescription.findAll", query = "SELECT j FROM Jobdescription j")
    , @NamedQuery(name = "Jobdescription.findByTitle", query = "SELECT j FROM Jobdescription j WHERE j.title = :title")
    , @NamedQuery(name = "Jobdescription.findById", query = "SELECT j FROM Jobdescription j WHERE j.id = :id")
    , @NamedQuery(name = "Jobdescription.findByKeywords", query = "SELECT j FROM Jobdescription j WHERE j.keywords = :keywords")
    , @NamedQuery(name = "Jobdescription.findByDescription", query = "SELECT j FROM Jobdescription j WHERE j.description = :description")
    , @NamedQuery(name = "Jobdescription.findByPayment", query = "SELECT j FROM Jobdescription j WHERE j.payment = :payment")
    , @NamedQuery(name = "Jobdescription.findByState", query = "SELECT j FROM Jobdescription j WHERE j.state = :state")
    , @NamedQuery(name = "Jobdescription.findByIdProvider", query = "SELECT j FROM Jobdescription j WHERE j.idProvider = :idProvider")})
public class Jobdescription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "TITLE")
    private String title;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "KEYWORDS")
    private String keywords;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PAYMENT")
    private Integer payment;
    @Size(max = 255)
    @Column(name = "STATE")
    private String state;
    @Column(name = "ID_PROVIDER")
    private Integer idProvider;

    public Jobdescription() {
    }

    public Jobdescription(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jobdescription)) {
            return false;
        }
        Jobdescription other = (Jobdescription) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "localSessionBeans.Jobdescription[ id=" + id + " ]";
    }
    
}
