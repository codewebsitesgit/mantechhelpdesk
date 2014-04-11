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
@Table(name = "ComplaintCategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComplaintCategory.findAll", query = "SELECT c FROM ComplaintCategory c"),
    @NamedQuery(name = "ComplaintCategory.findByCategoryID", query = "SELECT c FROM ComplaintCategory c WHERE c.categoryID = :categoryID"),
    @NamedQuery(name = "ComplaintCategory.findByCategoryName", query = "SELECT c FROM ComplaintCategory c WHERE c.categoryName = :categoryName"),
    @NamedQuery(name = "ComplaintCategory.findByCategoryDesc", query = "SELECT c FROM ComplaintCategory c WHERE c.categoryDesc = :categoryDesc")})
public class ComplaintCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CategoryID")
    private Integer categoryID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CategoryName")
    private String categoryName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "CategoryDesc")
    private String categoryDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "complaintCategory")
    private Collection<Complaint> complaintCollection;

    public ComplaintCategory() {
    }

    public ComplaintCategory(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public ComplaintCategory(Integer categoryID, String categoryName, String categoryDesc) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @XmlTransient
    public Collection<Complaint> getComplaintCollection() {
        return complaintCollection;
    }

    public void setComplaintCollection(Collection<Complaint> complaintCollection) {
        this.complaintCollection = complaintCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryID != null ? categoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComplaintCategory)) {
            return false;
        }
        ComplaintCategory other = (ComplaintCategory) object;
        if ((this.categoryID == null && other.categoryID != null) || (this.categoryID != null && !this.categoryID.equals(other.categoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.ComplaintCategory[ categoryID=" + categoryID + " ]";
    }
    
}
