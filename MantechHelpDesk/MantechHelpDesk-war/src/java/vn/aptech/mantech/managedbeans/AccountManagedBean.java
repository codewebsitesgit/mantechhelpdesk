/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import vn.aptech.mantech.constants.MantechConstants;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.sessionbeans.DepartmentFacadeLocal;
import vn.aptech.mantech.sessionbeans.UserAccountFacadeLocal;
import vn.aptech.mantech.sessionbeans.UserRoleFacadeLocal;
import vn.aptech.mantech.utils.PasswordUtils;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "account")
@SessionScoped
public class AccountManagedBean implements Serializable {

    @EJB
    private UserRoleFacadeLocal userRoleFacade;
    @EJB
    private DepartmentFacadeLocal departmentFacade;
    @EJB
    private UserAccountFacadeLocal userAccountFacade;

    private String username;
    private String password;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    private Integer searchAccountID;
    private String searchUsername;
    private Integer searchDepartmentID;
    private String searchFullName;
    private UserAccount selectAccount;

    private UserAccount curUser;
    private int newDepartmentID;
    private int newRoleID;

    @Resource
    UserTransaction ut;

    public String createUser() {
        if (!userAccountFacade.getAllAccountWithAdmin(0, curUser.getUsername(), null, null).isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("insertAccountForm:Username",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username is existed in database.", null));
            return "";
        }
        if (!userAccountFacade.getAllAccountWithAdmin(0, null, null, curUser.getName()).isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("insertAccountForm:Name",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name is existed in database.", null));
            return "";
        }
        curUser.setDepartmentID(departmentFacade.find(newDepartmentID));
        curUser.setRoleID(userRoleFacade.find(newRoleID));
        curUser.setPassword(PasswordUtils.hashPassword(curUser.getPassword()));
        userAccountFacade.create(curUser);
        return "viewAccounts?faces-redirect=true";
    }

    public String editNav() {
        newDepartmentID = curUser.getDepartmentID().getDepartmentID();
        newRoleID = curUser.getRoleID().getRoleID();
        return "editAccount?faces-redirect=true";
    }

    public String editUser() {
        if (!userAccountFacade.getAllAccount(curUser.getAccountID(), curUser.getName()).isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("insertAccountForm:Name",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Name is existed in database.", null));
            return "";
        }
        curUser.setDepartmentID(departmentFacade.find(newDepartmentID));
        curUser.setRoleID(userRoleFacade.find(newRoleID));
        userAccountFacade.edit(curUser);
        return "viewAccounts?faces-redirect=true";
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Creates a new instance of AccountManagedBean
     */
    public AccountManagedBean() {
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String checkLogin() {
        UserAccount account = userAccountFacade.getUserAccount(username);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        String hashPassword = PasswordUtils.hashPassword(password);
        if (account != null && account.getPassword().equals(hashPassword)) {
            session.setAttribute("userSession", account);
            if (account.getRoleID().getRoleID() == MantechConstants.ROLE_ADMIN) {
                return "viewLastModifiedComplaints?faces-redirect=true";
            }
            if (account.getRoleID().getRoleID() == MantechConstants.ROLE_USER) {
                return "viewComplaint?faces-redirect=true";
            }
            if (account.getRoleID().getRoleID() == MantechConstants.ROLE_TECHNICIAN) {
                return "viewComplaintAssignment?faces-redirect=true";
            }
        }
        session.setAttribute("userSession", null);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Invalid Username or Password!", "Please check again input Username and Password!"));
        return "index?faces-redirect=true";
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);

        if (session != null) {
            session.removeAttribute("userSession");
            session.invalidate();
        }
        return "index?faces-redirect=true";
    }

    public String changeUserPassword() {
        return "changeUserPassword?faces-redirect=true";
    }

    public String newAccount() {
        curUser = new UserAccount(userAccountFacade.getNextAccountID());
        curUser.setStatus(true);
        return "newAccount?faces-redirect=true";
    }

    public String viewAccounts() {
        return "viewAccounts?faces-redirect=true";
    }

    public List<UserAccount> getAllTechnicians() {
        return userAccountFacade.getAllTechnicians();
    }

    public String changePassword() {

        // check the old password is the same with input
        String hashPassword = PasswordUtils.hashPassword(oldPassword);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        UserAccount account = (UserAccount) session.getAttribute("userSession");
        if (account == null) {
            return "index?faces-redirect=true";
        }

        if (!newPassword.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Confirm password does not match with New password!",
                    "Please enter again your Confirm password!"));
            return "changeUserPassword?faces-redirect=true";
        }
        UserAccount updateAcc = userAccountFacade.find(account.getAccountID());
        if (updateAcc.getPassword().equals(hashPassword)) {
            String newPasswordHashed = PasswordUtils.hashPassword(newPassword);
            try {
                ut.begin();

                updateAcc.setPassword(newPasswordHashed);
                userAccountFacade.edit(updateAcc);
                ut.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, "Change password successfully!",
                        "Change password successfully!"));
            } catch (Exception e) {
                try {
                    ut.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Old password is not correct!",
                    "Please enter again your old password!"));
        }
        return "changeUserPassword?faces-redirect=true";
    }

    public List<UserAccount> getSearchAllAccount() {
        return userAccountFacade.getAllAccount(searchAccountID, searchUsername, searchDepartmentID, searchFullName);
    }

    /**
     * @return the curUser
     */
    public UserAccount getCurUser() {
        return curUser;
    }

    /**
     * @param curUser the curUser to set
     */
    public void setCurUser(UserAccount curUser) {
        this.curUser = curUser;
    }

    /**
     * @return the searchAccountID
     */
    public Integer getSearchAccountID() {
        return searchAccountID;
    }

    /**
     * @param searchAccountID the searchAccountID to set
     */
    public void setSearchAccountID(Integer searchAccountID) {
        this.searchAccountID = searchAccountID;
    }

    /**
     * @return the searchUsername
     */
    public String getSearchUsername() {
        return searchUsername;
    }

    /**
     * @param searchUsername the searchUsername to set
     */
    public void setSearchUsername(String searchUsername) {
        this.searchUsername = searchUsername;
    }

    /**
     * @return the searchDepartmentID
     */
    public Integer getSearchDepartmentID() {
        return searchDepartmentID;
    }

    /**
     * @param searchDepartmentID the searchDepartmentID to set
     */
    public void setSearchDepartmentID(Integer searchDepartmentID) {
        this.searchDepartmentID = searchDepartmentID;
    }

    /**
     * @return the searchFullName
     */
    public String getSearchFullName() {
        return searchFullName;
    }

    /**
     * @param searchFullName the searchFullName to set
     */
    public void setSearchFullName(String searchFullName) {
        this.searchFullName = searchFullName;
    }

    /**
     * @return the selectAccount
     */
    public UserAccount getSelectAccount() {
        return selectAccount;
    }

    /**
     * @param selectAccount the selectAccount to set
     */
    public void setSelectAccount(UserAccount selectAccount) {
        this.selectAccount = selectAccount;
    }

    /**
     * @return the newRoleID
     */
    public int getNewRoleID() {
        return newRoleID;
    }

    /**
     * @param newRoleID the newRoleID to set
     */
    public void setNewRoleID(int newRoleID) {
        this.newRoleID = newRoleID;
    }

    /**
     * @return the newDepartmentID
     */
    public int getNewDepartmentID() {
        return newDepartmentID;
    }

    /**
     * @param newDepartmentID the newDepartmentID to set
     */
    public void setNewDepartmentID(int newDepartmentID) {
        this.newDepartmentID = newDepartmentID;
    }

    public String displayChangeUserProfile() {
        return "changeUserProfile";
    }
}
