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
@Table(name = "ComplaintHistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComplaintHistory.findAll", query = "SELECT c FROM ComplaintHistory c"),
    @NamedQuery(name = "ComplaintHistory.findByHistoryID", query = "SELECT c FROM ComplaintHistory c WHERE c.historyID = :historyID"),
    @NamedQuery(name = "ComplaintHistory.findByLastModifiedDate", query = "SELECT c FROM ComplaintHistory c WHERE c.lastModifiedDate = :lastModifiedDate")})
public class ComplaintHistory implements Serializable {
    @Size(max = 300)
    @Column(name = "Details")
    private String details;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HistoryID")
    private Integer historyID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LastModifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @JoinColumn(name = "UserAccountID", referencedColumnName = "AccountID")
    @ManyToOne(optional = false)
    private UserAccount userAccountID;
    @JoinColumn(name = "ComplaintID", referencedColumnName = "ComplaintID")
    @ManyToOne(optional = false)
    private Complaint complaintID;
    @JoinColumn(name = "ActionID", referencedColumnName = "ActionID")
    @ManyToOne(optional = false)
    private Activity actionID;

    public ComplaintHistory() {
    }

    public ComplaintHistory(Integer historyID) {
        this.historyID = historyID;
    }

    public ComplaintHistory(Integer historyID, Date lastModifiedDate) {
        this.historyID = historyID;
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getHistoryID() {
        return historyID;
    }

    public void setHistoryID(Integer historyID) {
        this.historyID = historyID;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserAccount getUserAccountID() {
        return userAccountID;
    }

    public void setUserAccountID(UserAccount userAccountID) {
        this.userAccountID = userAccountID;
    }

    public Complaint getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(Complaint complaintID) {
        this.complaintID = complaintID;
    }

    public Activity getActionID() {
        return actionID;
    }

    public void setActionID(Activity actionID) {
        this.actionID = actionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historyID != null ? historyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComplaintHistory)) {
            return false;
        }
        ComplaintHistory other = (ComplaintHistory) object;
        if ((this.historyID == null && other.historyID != null) || (this.historyID != null && !this.historyID.equals(other.historyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.ComplaintHistory[ historyID=" + historyID + " ]";
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
}
