/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TruongLQ
 */
@Entity
@Table(name = "Article")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
    @NamedQuery(name = "Article.findByArticleID", query = "SELECT a FROM Article a WHERE a.articleID = :articleID"),
    @NamedQuery(name = "Article.findByArticleSubject", query = "SELECT a FROM Article a WHERE a.articleSubject = :articleSubject"),
    @NamedQuery(name = "Article.findByArticleContents", query = "SELECT a FROM Article a WHERE a.articleContents = :articleContents"),
    @NamedQuery(name = "Article.findByCreationDate", query = "SELECT a FROM Article a WHERE a.creationDate = :creationDate"),
    @NamedQuery(name = "Article.findByStatus", query = "SELECT a FROM Article a WHERE a.status = :status")})
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ArticleID")
    private Integer articleID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "ArticleSubject")
    private String articleSubject;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "ArticleContents")
    private String articleContents;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private boolean status;
    @JoinColumn(name = "ArticleOwner", referencedColumnName = "AccountID")
    @ManyToOne(optional = false)
    private UserAccount articleOwner;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    private Collection<ArticleRate> articleRateCollection;

    public Article() {
    }

    public Article(Integer articleID) {
        this.articleID = articleID;
    }

    public Article(Integer articleID, String articleSubject, String articleContents, Date creationDate, boolean status) {
        this.articleID = articleID;
        this.articleSubject = articleSubject;
        this.articleContents = articleContents;
        this.creationDate = creationDate;
        this.status = status;
    }

    public Integer getArticleID() {
        return articleID;
    }

    public void setArticleID(Integer articleID) {
        this.articleID = articleID;
    }

    public String getArticleSubject() {
        return articleSubject;
    }

    public void setArticleSubject(String articleSubject) {
        this.articleSubject = articleSubject;
    }

    public String getArticleContents() {
        return articleContents;
    }

    public void setArticleContents(String articleContents) {
        this.articleContents = articleContents;
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

    public UserAccount getArticleOwner() {
        return articleOwner;
    }

    public void setArticleOwner(UserAccount articleOwner) {
        this.articleOwner = articleOwner;
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
        hash += (articleID != null ? articleID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.articleID == null && other.articleID != null) || (this.articleID != null && !this.articleID.equals(other.articleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.Article[ articleID=" + articleID + " ]";
    }
    
    @Transient
    private ArticleRate currentRate;

    /**
     * @return the currentRate
     */
    public ArticleRate getCurrentRate() {
        return currentRate;
    }

    /**
     * @param currentRate the currentRate to set
     */
    public void setCurrentRate(ArticleRate currentRate) {
        this.currentRate = currentRate;
    }

    
}
