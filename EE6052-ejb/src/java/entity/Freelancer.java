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
import javax.persistence.Lob;
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
@Table(name = "FREELANCER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Freelancer.findMaxId",query = "SELECT MAX(p.id) FROM Freelancer p")
    , @NamedQuery(name = "Freelancer.getFreelancerID", query = "SELECT u.id FROM Freelancer u WHERE u.username = :username AND u.password = :password") 
    , @NamedQuery(name = "Freelancer.validate", query = "SELECT COUNT(c) FROM Freelancer c WHERE c.username = :username AND c.password = :password")
    , @NamedQuery(name = "Freelancer.isFreelancerExists", query = "SELECT COUNT(c) FROM Freelancer c WHERE c.username = :username")
  
    , @NamedQuery(name = "Freelancer.findAll", query = "SELECT f FROM Freelancer f")
    , @NamedQuery(name = "Freelancer.findByUsername", query = "SELECT f FROM Freelancer f WHERE f.username = :username")
    , @NamedQuery(name = "Freelancer.findByPassword", query = "SELECT f FROM Freelancer f WHERE f.password = :password")
    , @NamedQuery(name = "Freelancer.findById", query = "SELECT f FROM Freelancer f WHERE f.id = :id")
    , @NamedQuery(name = "Freelancer.findBySkill", query = "SELECT f FROM Freelancer f WHERE f.skill = :skill")
    , @NamedQuery(name = "Freelancer.findByName", query = "SELECT f FROM Freelancer f WHERE f.name = :name")
    , @NamedQuery(name = "Freelancer.findByIdJobIntent", query = "SELECT f FROM Freelancer f WHERE f.idJobIntent = :idJobIntent")
    , @NamedQuery(name = "Freelancer.findByPayment", query = "SELECT f FROM Freelancer f WHERE f.payment = :payment")})
public class Freelancer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "SKILL")
    private String skill;
    @Lob
    @Size(max = 32700)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;
    @Column(name = "ID_JOB_INTENT")
    private Integer idJobIntent;
    @Column(name = "PAYMENT")
    private Integer payment;

    public Freelancer() {
    }

    public Freelancer(Integer id) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Freelancer)) {
            return false;
        }
        Freelancer other = (Freelancer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "localSessionBeans.Freelancer[ id=" + id + ", username="+ username +", password="+password+", name="+name+", skill="+skill+", dec="+description+" ]";
    }
    
}
