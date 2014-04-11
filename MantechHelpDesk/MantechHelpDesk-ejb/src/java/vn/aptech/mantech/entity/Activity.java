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
@Table(name = "Activity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Activity.findAll", query = "SELECT a FROM Activity a"),
    @NamedQuery(name = "Activity.findByActionID", query = "SELECT a FROM Activity a WHERE a.actionID = :actionID"),
    @NamedQuery(name = "Activity.findByActionDesc", query = "SELECT a FROM Activity a WHERE a.actionDesc = :actionDesc")})
public class Activity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ActionID")
    private Integer actionID;
    @Size(max = 150)
    @Column(name = "ActionDesc")
    private String actionDesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actionID")
    private Collection<ComplaintHistory> complaintHistoryCollection;

    public Activity() {
    }

    public Activity(Integer actionID) {
        this.actionID = actionID;
    }

    public Integer getActionID() {
        return actionID;
    }

    public void setActionID(Integer actionID) {
        this.actionID = actionID;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    @XmlTransient
    public Collection<ComplaintHistory> getComplaintHistoryCollection() {
        return complaintHistoryCollection;
    }

    public void setComplaintHistoryCollection(Collection<ComplaintHistory> complaintHistoryCollection) {
        this.complaintHistoryCollection = complaintHistoryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actionID != null ? actionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Activity)) {
            return false;
        }
        Activity other = (Activity) object;
        if ((this.actionID == null && other.actionID != null) || (this.actionID != null && !this.actionID.equals(other.actionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.Activity[ actionID=" + actionID + " ]";
    }
    
}
