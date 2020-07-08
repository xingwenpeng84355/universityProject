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
@Table(name = "PROVIDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provider.findMaxId",query = "SELECT MAX(p.id) FROM Provider p")
    , @NamedQuery(name = "Provider.getProviderID", query = "SELECT u.id FROM Provider u WHERE u.username = :username AND u.password = :password") 
    , @NamedQuery(name = "Provider.validate", query = "SELECT COUNT(c) FROM Provider c WHERE c.username = :username AND c.password = :password")
    , @NamedQuery(name = "Provider.isProviderExists", query = "SELECT COUNT(c) FROM Provider c WHERE c.username = :username")
        
    , @NamedQuery(name = "Provider.findAll", query = "SELECT p FROM Provider p")
    , @NamedQuery(name = "Provider.findById", query = "SELECT p FROM Provider p WHERE p.id = :id")
    , @NamedQuery(name = "Provider.findByUsername", query = "SELECT p FROM Provider p WHERE p.username = :username")
    , @NamedQuery(name = "Provider.findByPassword", query = "SELECT p FROM Provider p WHERE p.password = :password")})
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;

    public Provider() {
    }

    public Provider(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provider)) {
            return false;
        }
        Provider other = (Provider) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "localSessionBeans.Provider[ id=" + id + " ]";
    }
    
}
