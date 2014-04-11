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
@Table(name = "ComplaintPriority")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComplaintPriority.findAll", query = "SELECT c FROM ComplaintPriority c"),
    @NamedQuery(name = "ComplaintPriority.findByPriorityID", query = "SELECT c FROM ComplaintPriority c WHERE c.priorityID = :priorityID"),
    @NamedQuery(name = "ComplaintPriority.findByPriorityName", query = "SELECT c FROM ComplaintPriority c WHERE c.priorityName = :priorityName"),
    @NamedQuery(name = "ComplaintPriority.findByPriorityDesc", query = "SELECT c FROM ComplaintPriority c WHERE c.priorityDesc = :priorityDesc")})
public class ComplaintPriority implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PriorityID")
    private Integer priorityID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PriorityName")
    private String priorityName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "PriorityDesc")
    private String priorityDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priority")
    private Collection<Complaint> complaintCollection;

    public ComplaintPriority() {
    }

    public ComplaintPriority(Integer priorityID) {
        this.priorityID = priorityID;
    }

    public ComplaintPriority(Integer priorityID, String priorityName, String priorityDesc) {
        this.priorityID = priorityID;
        this.priorityName = priorityName;
        this.priorityDesc = priorityDesc;
    }

    public Integer getPriorityID() {
        return priorityID;
    }

    public void setPriorityID(Integer priorityID) {
        this.priorityID = priorityID;
    }

    public String getPriorityName() {
        return priorityName;
    }

    public void setPriorityName(String priorityName) {
        this.priorityName = priorityName;
    }

    public String getPriorityDesc() {
        return priorityDesc;
    }

    public void setPriorityDesc(String priorityDesc) {
        this.priorityDesc = priorityDesc;
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
        hash += (priorityID != null ? priorityID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComplaintPriority)) {
            return false;
        }
        ComplaintPriority other = (ComplaintPriority) object;
        if ((this.priorityID == null && other.priorityID != null) || (this.priorityID != null && !this.priorityID.equals(other.priorityID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.ComplaintPriority[ priorityID=" + priorityID + " ]";
    }
    
}
