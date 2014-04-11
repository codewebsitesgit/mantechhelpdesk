/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TruongLQ
 */
@Entity
@Table(name = "Faqs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Faqs.findAll", query = "SELECT f FROM Faqs f"),
    @NamedQuery(name = "Faqs.findByFaqID", query = "SELECT f FROM Faqs f WHERE f.faqID = :faqID"),
    @NamedQuery(name = "Faqs.findByFaqSubject", query = "SELECT f FROM Faqs f WHERE f.faqSubject = :faqSubject"),
    @NamedQuery(name = "Faqs.findByFaqContents", query = "SELECT f FROM Faqs f WHERE f.faqContents = :faqContents"),
    @NamedQuery(name = "Faqs.findByCreationDate", query = "SELECT f FROM Faqs f WHERE f.creationDate = :creationDate"),
    @NamedQuery(name = "Faqs.findByStatus", query = "SELECT f FROM Faqs f WHERE f.status = :status")})
public class Faqs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FaqID")
    private Integer faqID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "FaqSubject")
    private String faqSubject;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "FaqContents")
    private String faqContents;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private boolean status;

    public Faqs() {
    }

    public Faqs(Integer faqID) {
        this.faqID = faqID;
    }

    public Faqs(Integer faqID, String faqSubject, String faqContents, Date creationDate, boolean status) {
        this.faqID = faqID;
        this.faqSubject = faqSubject;
        this.faqContents = faqContents;
        this.creationDate = creationDate;
        this.status = status;
    }

    public Integer getFaqID() {
        return faqID;
    }

    public void setFaqID(Integer faqID) {
        this.faqID = faqID;
    }

    public String getFaqSubject() {
        return faqSubject;
    }

    public void setFaqSubject(String faqSubject) {
        this.faqSubject = faqSubject;
    }

    public String getFaqContents() {
        return faqContents;
    }

    public void setFaqContents(String faqContents) {
        this.faqContents = faqContents;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (faqID != null ? faqID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Faqs)) {
            return false;
        }
        Faqs other = (Faqs) object;
        if ((this.faqID == null && other.faqID != null) || (this.faqID != null && !this.faqID.equals(other.faqID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.Faqs[ faqID=" + faqID + " ]";
    }
    
}
