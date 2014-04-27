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
@Table(name = "Complaint")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Complaint.findAll", query = "SELECT c FROM Complaint c"),
    @NamedQuery(name = "Complaint.findByComplaintID", query = "SELECT c FROM Complaint c WHERE c.complaintID = :complaintID"),
    @NamedQuery(name = "Complaint.findByComplaintSubject", query = "SELECT c FROM Complaint c WHERE c.complaintSubject = :complaintSubject"),
    @NamedQuery(name = "Complaint.findByComplaintContents", query = "SELECT c FROM Complaint c WHERE c.complaintContents = :complaintContents"),
    @NamedQuery(name = "Complaint.findByLodgingDate", query = "SELECT c FROM Complaint c WHERE c.lodgingDate = :lodgingDate"),
    @NamedQuery(name = "Complaint.findByClosingDate", query = "SELECT c FROM Complaint c WHERE c.closingDate = :closingDate")})
public class Complaint implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "complaintID")
    private Collection<ComplaintImages> complaintImagesCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LastModified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;
    @Size(max = 300)
    @Column(name = "Reasons")
    private String reasons;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ComplaintID")
    private Integer complaintID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "ComplaintSubject")
    private String complaintSubject;
    @Basic(optional = false)
    @Size(max = 1000)
    @Column(name = "ComplaintContents")
    private String complaintContents;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LodgingDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lodgingDate;
    @Column(name = "ClosingDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closingDate;
    @JoinColumn(name = "Technician", referencedColumnName = "AccountID")
    @ManyToOne
    private UserAccount technician;
    @JoinColumn(name = "ComplaintOwner", referencedColumnName = "AccountID")
    @ManyToOne(optional = false)
    private UserAccount complaintOwner;
    @JoinColumn(name = "Status", referencedColumnName = "StatusID")
    @ManyToOne(optional = false)
    private ComplaintStatus status;
    @JoinColumn(name = "Priority", referencedColumnName = "PriorityID")
    @ManyToOne(optional = false)
    private ComplaintPriority priority;
    @JoinColumn(name = "ComplaintCategory", referencedColumnName = "CategoryID")
    @ManyToOne(optional = false)
    private ComplaintCategory complaintCategory;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "complaintID")
    private Collection<ComplaintHistory> complaintHistoryCollection;

    public Complaint() {
    }

    public Complaint(Integer complaintID) {
        this.complaintID = complaintID;
    }

    public Complaint(Integer complaintID, String complaintSubject, String complaintContents, Date lodgingDate) {
        this.complaintID = complaintID;
        this.complaintSubject = complaintSubject;
        this.complaintContents = complaintContents;
        this.lodgingDate = lodgingDate;
    }

    public Integer getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(Integer complaintID) {
        this.complaintID = complaintID;
    }

    public String getComplaintSubject() {
        return complaintSubject;
    }

    public void setComplaintSubject(String complaintSubject) {
        this.complaintSubject = complaintSubject;
    }

    public String getComplaintContents() {
        return complaintContents;
    }

    public void setComplaintContents(String complaintContents) {
        this.complaintContents = complaintContents;
    }

    public Date getLodgingDate() {
        return lodgingDate;
    }

    public void setLodgingDate(Date lodgingDate) {
        this.lodgingDate = lodgingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public UserAccount getTechnician() {
        return technician;
    }

    public void setTechnician(UserAccount technician) {
        this.technician = technician;
    }

    public UserAccount getComplaintOwner() {
        return complaintOwner;
    }

    public void setComplaintOwner(UserAccount complaintOwner) {
        this.complaintOwner = complaintOwner;
    }

    public ComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }

    public ComplaintPriority getPriority() {
        return priority;
    }

    public void setPriority(ComplaintPriority priority) {
        this.priority = priority;
    }

    public ComplaintCategory getComplaintCategory() {
        return complaintCategory;
    }

    public void setComplaintCategory(ComplaintCategory complaintCategory) {
        this.complaintCategory = complaintCategory;
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
        hash += (complaintID != null ? complaintID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Complaint)) {
            return false;
        }
        Complaint other = (Complaint) object;
        if ((this.complaintID == null && other.complaintID != null) || (this.complaintID != null && !this.complaintID.equals(other.complaintID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.Complaint[ complaintID=" + complaintID + " ]";
    }
    
    @Transient
    private boolean resend;

    public boolean isResend() {
        return resend;
    }

    public void setResend(boolean resend) {
        this.resend = resend;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
    
    @Transient
    private long actualTakenDays;

    @Transient
    private long actualTakenHours;
    
    @Transient
    private long actualTakenMinutes;
    
    @Transient
    private long actualTakenSeconds;

    public long getActualTakenDays() {
        return actualTakenDays;
    }

    public void setActualTakenDays(long actualTakenDays) {
        this.actualTakenDays = actualTakenDays;
    }

    public long getActualTakenHours() {
        return actualTakenHours;
    }

    public void setActualTakenHours(long actualTakenHours) {
        this.actualTakenHours = actualTakenHours;
    }

    public long getActualTakenMinutes() {
        return actualTakenMinutes;
    }

    public void setActualTakenMinutes(long actualTakenMinutes) {
        this.actualTakenMinutes = actualTakenMinutes;
    }

    public long getActualTakenSeconds() {
        return actualTakenSeconds;
    }

    public void setActualTakenSeconds(long actualTakenSeconds) {
        this.actualTakenSeconds = actualTakenSeconds;
    }

    @XmlTransient
    public Collection<ComplaintImages> getComplaintImagesCollection() {
        return complaintImagesCollection;
    }

    public void setComplaintImagesCollection(Collection<ComplaintImages> complaintImagesCollection) {
        this.complaintImagesCollection = complaintImagesCollection;
    }
    
}
