/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author TruongLQ
 */
@Embeddable
public class ArticleRatePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ArticleID")
    private int articleID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RateID")
    private int rateID;

    public ArticleRatePK() {
    }

    public ArticleRatePK(int articleID, int rateID) {
        this.articleID = articleID;
        this.rateID = rateID;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public int getRateID() {
        return rateID;
    }

    public void setRateID(int rateID) {
        this.rateID = rateID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) articleID;
        hash += (int) rateID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticleRatePK)) {
            return false;
        }
        ArticleRatePK other = (ArticleRatePK) object;
        if (this.articleID != other.articleID) {
            return false;
        }
        if (this.rateID != other.rateID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.ArticleRatePK[ articleID=" + articleID + ", rateID=" + rateID + " ]";
    }
    
}
