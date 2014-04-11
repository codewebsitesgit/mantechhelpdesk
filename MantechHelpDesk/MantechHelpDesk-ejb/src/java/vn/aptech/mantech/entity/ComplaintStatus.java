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
@Table(name = "ComplaintStatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComplaintStatus.findAll", query = "SELECT c FROM ComplaintStatus c"),
    @NamedQuery(name = "ComplaintStatus.findByStatusID", query = "SELECT c FROM ComplaintStatus c WHERE c.statusID = :statusID"),
    @NamedQuery(name = "ComplaintStatus.findByStatusName", query = "SELECT c FROM ComplaintStatus c WHERE c.statusName = :statusName"),
    @NamedQuery(name = "ComplaintStatus.findByStatusDesc", query = "SELECT c FROM ComplaintStatus c WHERE c.statusDesc = :statusDesc")})
public class ComplaintStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "StatusID")
    private Integer statusID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "StatusName")
    private String statusName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "StatusDesc")
    private String statusDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Complaint> complaintCollection;

    public ComplaintStatus() {
    }

    public ComplaintStatus(Integer statusID) {
        this.statusID = statusID;
    }

    public ComplaintStatus(Integer statusID, String statusName, String statusDesc) {
        this.statusID = statusID;
        this.statusName = statusName;
        this.statusDesc = statusDesc;
    }

    public Integer getStatusID() {
        return statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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
        hash += (statusID != null ? statusID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComplaintStatus)) {
            return false;
        }
        ComplaintStatus other = (ComplaintStatus) object;
        if ((this.statusID == null && other.statusID != null) || (this.statusID != null && !this.statusID.equals(other.statusID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.ComplaintStatus[ statusID=" + statusID + " ]";
    }
    
}
