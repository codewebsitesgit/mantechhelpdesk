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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author TruongLQ
 */
@Entity
@Table(name = "UserAccount")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u"),
    @NamedQuery(name = "UserAccount.findByAccountID", query = "SELECT u FROM UserAccount u WHERE u.accountID = :accountID"),
    @NamedQuery(name = "UserAccount.findByUsername", query = "SELECT u FROM UserAccount u WHERE u.username = :username"),
    @NamedQuery(name = "UserAccount.findByPassword", query = "SELECT u FROM UserAccount u WHERE u.password = :password"),
    @NamedQuery(name = "UserAccount.findByName", query = "SELECT u FROM UserAccount u WHERE u.name = :name"),
    @NamedQuery(name = "UserAccount.findByBirthday", query = "SELECT u FROM UserAccount u WHERE u.birthday = :birthday"),
    @NamedQuery(name = "UserAccount.findByGender", query = "SELECT u FROM UserAccount u WHERE u.gender = :gender"),
    @NamedQuery(name = "UserAccount.findByAddress", query = "SELECT u FROM UserAccount u WHERE u.address = :address"),
    @NamedQuery(name = "UserAccount.findByPhone", query = "SELECT u FROM UserAccount u WHERE u.phone = :phone"),
    @NamedQuery(name = "UserAccount.findByEmail", query = "SELECT u FROM UserAccount u WHERE u.email = :email"),
    @NamedQuery(name = "UserAccount.findByStatus", query = "SELECT u FROM UserAccount u WHERE u.status = :status")})
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AccountID")
    private Integer accountID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Gender")
    private short gender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Address")
    private String address;
    @Pattern(regexp = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message = "Invalid phone number format, should be as (xxx) xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Phone")
    private String phone;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email format")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private boolean status;
    @JoinColumn(name = "RoleID", referencedColumnName = "RoleID")
    @ManyToOne(optional = false)
    private UserRole roleID;
    @JoinColumn(name = "DepartmentID", referencedColumnName = "DepartmentID")
    @ManyToOne(optional = false)
    private Department departmentID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleOwner")
    private Collection<Article> articleCollection;
    @OneToMany(mappedBy = "technician")
    private Collection<Complaint> complaintCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "complaintOwner")
    private Collection<Complaint> complaintCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAccountID")
    private Collection<ComplaintHistory> complaintHistoryCollection;

    public UserAccount() {
    }

    public UserAccount(Integer accountID) {
        this.accountID = accountID;
    }

    public UserAccount(Integer accountID, String username, String password, String name, Date birthday, short gender, String address, String phone, String email, boolean status) {
        this.accountID = accountID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public short getGender() {
        return gender;
    }

    public String getGenderName() {
        switch (gender) {
            case 0:
                return "Unknown";
            case 1:
                return "Male";
            case 2:
                return "Female";
        }
        return "";
    }

    public void setGender(short gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserRole getRoleID() {
        return roleID;
    }

    public void setRoleID(UserRole roleID) {
        this.roleID = roleID;
    }

    public Department getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Department departmentID) {
        this.departmentID = departmentID;
    }

    @XmlTransient
    public Collection<Article> getArticleCollection() {
        return articleCollection;
    }

    public void setArticleCollection(Collection<Article> articleCollection) {
        this.articleCollection = articleCollection;
    }

    @XmlTransient
    public Collection<Complaint> getComplaintCollection() {
        return complaintCollection;
    }

    public void setComplaintCollection(Collection<Complaint> complaintCollection) {
        this.complaintCollection = complaintCollection;
    }

    @XmlTransient
    public Collection<Complaint> getComplaintCollection1() {
        return complaintCollection1;
    }

    public void setComplaintCollection1(Collection<Complaint> complaintCollection1) {
        this.complaintCollection1 = complaintCollection1;
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
        hash += (accountID != null ? accountID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserAccount)) {
            return false;
        }
        UserAccount other = (UserAccount) object;
        if ((this.accountID == null && other.accountID != null) || (this.accountID != null && !this.accountID.equals(other.accountID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "vn.aptech.mantech.entity.UserAccount[ accountID=" + accountID + " ]";
    }

}
