/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TruongLQ
 */
@Entity
@Table(name = "Rate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rate.findAll", query = "SELECT r FROM Rate r"),
    @NamedQuery(name = "Rate.findByRateID", query = "SELECT r FROM Rate r WHERE r.rateID = :rateID"),
    @NamedQuery(name = "Rate.findByRateName", query = "SELECT r FROM Rate r WHERE r.rateName = :rateName"),
    @NamedQuery(name = "Rate.findByRateDesc", query = "SELECT r FROM Rate r WHERE r.rateDesc = :rateDesc")})
public class Rate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RateID")
    private Integer rateID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "RateName")
    private String rateName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "RateDesc")
    private String rateDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rate")
    private Collection<ArticleRate> articleRateCollection;

    public Rate() {
    }

    public Rate(Integer rateID) {
        this.rateID = rateID;
    }

    public Rate(Integer rateID, String rateName, String rateDesc) {
        this.rateID = rateID;
        this.rateName = rateName;
        this.rateDesc = rateDesc;
    }

    public Integer getRateID() {
        return rateID;
    }

    public void setRateID(Integer rateID) {
        this.rateID = rateID;
    }

    public String getRateName() {
        return rateName;
    }

    public void setRateName(String rateName) {
        this.rateName = rateName;
    }

    public String getRateDesc() {
        return rateDesc;
    }

    public void setRateDesc(String rateDesc) {
        this.rateDesc = rateDesc;
    }

    @XmlTransient
    public Collection<ArticleRate> getArticleRateCollection() {
        return articleRateCollection;
    }

    public void setArticleRateCollection(Collection<ArticleRate> articleRateCollection) {
        this.articleRateCollection = articleRateCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rateID != null ? rateID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rate)) {
            return false;
        }
        Rate other = (Rate) object;
        if ((this.rateID == null && other.rateID != null) || (this.rateID != null && !this.rateID.equals(other.rateID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.Rate[ rateID=" + rateID + " ]";
    }
    
}
