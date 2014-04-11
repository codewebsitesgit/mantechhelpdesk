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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author TruongLQ
 */
@Entity
@Table(name = "ArticleRate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticleRate.findAll", query = "SELECT a FROM ArticleRate a"),
    @NamedQuery(name = "ArticleRate.findByArticleID", query = "SELECT a FROM ArticleRate a WHERE a.articleRatePK.articleID = :articleID"),
    @NamedQuery(name = "ArticleRate.findByRateID", query = "SELECT a FROM ArticleRate a WHERE a.articleRatePK.rateID = :rateID"),
    @NamedQuery(name = "ArticleRate.findByCreationDate", query = "SELECT a FROM ArticleRate a WHERE a.creationDate = :creationDate"),
    @NamedQuery(name = "ArticleRate.findByRateOwner", query = "SELECT a FROM ArticleRate a WHERE a.rateOwner = :rateOwner")})
public class ArticleRate implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ArticleRatePK articleRatePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RateOwner")
    private int rateOwner;
    @JoinColumn(name = "RateID", referencedColumnName = "RateID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rate rate;
    @JoinColumn(name = "ArticleID", referencedColumnName = "ArticleID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Article article;

    public ArticleRate() {
    }

    public ArticleRate(ArticleRatePK articleRatePK) {
        this.articleRatePK = articleRatePK;
    }

    public ArticleRate(ArticleRatePK articleRatePK, Date creationDate, int rateOwner) {
        this.articleRatePK = articleRatePK;
        this.creationDate = creationDate;
        this.rateOwner = rateOwner;
    }

    public ArticleRate(int articleID, int rateID) {
        this.articleRatePK = new ArticleRatePK(articleID, rateID);
    }

    public ArticleRatePK getArticleRatePK() {
        return articleRatePK;
    }

    public void setArticleRatePK(ArticleRatePK articleRatePK) {
        this.articleRatePK = articleRatePK;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getRateOwner() {
        return rateOwner;
    }

    public void setRateOwner(int rateOwner) {
        this.rateOwner = rateOwner;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (articleRatePK != null ? articleRatePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticleRate)) {
            return false;
        }
        ArticleRate other = (ArticleRate) object;
        if ((this.articleRatePK == null && other.articleRatePK != null) || (this.articleRatePK != null && !this.articleRatePK.equals(other.articleRatePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.ArticleRate[ articleRatePK=" + articleRatePK + " ]";
    }
    
}
