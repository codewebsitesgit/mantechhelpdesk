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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ComplaintImages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComplaintImages.findAll", query = "SELECT c FROM ComplaintImages c"),
    @NamedQuery(name = "ComplaintImages.findByComplaintImageID", query = "SELECT c FROM ComplaintImages c WHERE c.complaintImageID = :complaintImageID"),
    @NamedQuery(name = "ComplaintImages.findByImageLocation", query = "SELECT c FROM ComplaintImages c WHERE c.imageLocation = :imageLocation"),
    @NamedQuery(name = "ComplaintImages.findByCreationDate", query = "SELECT c FROM ComplaintImages c WHERE c.creationDate = :creationDate")})
public class ComplaintImages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ComplaintImageID")
    private Integer complaintImageID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 350)
    @Column(name = "ImageLocation")
    private String imageLocation;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CreationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @JoinColumn(name = "ComplaintID", referencedColumnName = "ComplaintID")
    @ManyToOne(optional = false)
    private Complaint complaintID;

    public ComplaintImages() {
    }

    public ComplaintImages(Integer complaintImageID) {
        this.complaintImageID = complaintImageID;
    }

    public ComplaintImages(Integer complaintImageID, String imageLocation, Date creationDate) {
        this.complaintImageID = complaintImageID;
        this.imageLocation = imageLocation;
        this.creationDate = creationDate;
    }

    public Integer getComplaintImageID() {
        return complaintImageID;
    }

    public void setComplaintImageID(Integer complaintImageID) {
        this.complaintImageID = complaintImageID;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Complaint getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(Complaint complaintID) {
        this.complaintID = complaintID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (complaintImageID != null ? complaintImageID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComplaintImages)) {
            return false;
        }
        ComplaintImages other = (ComplaintImages) object;
        if ((this.complaintImageID == null && other.complaintImageID != null) || (this.complaintImageID != null && !this.complaintImageID.equals(other.complaintImageID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.ComplaintImages[ complaintImageID=" + complaintImageID + " ]";
    }
    
}
