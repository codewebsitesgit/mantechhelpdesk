/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
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
import vn.aptech.mantech.sessionbeans.UserAccountFacadeLocal;
import vn.aptech.mantech.utils.PasswordUtils;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "account")
@SessionScoped
public class AccountManagedBean implements Serializable {

    @EJB
    private UserAccountFacadeLocal userAccountFacade;

    private String username;
    private String password;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    private UserAccount curUser;

    @Resource
    UserTransaction ut;

    public String createUser() {
        //userAccountFacade.create(curUser);
        return "viewAccounts";
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
                return "administrator";
            }
            if (account.getRoleID().getRoleID() == MantechConstants.ROLE_USER) {
                return "registeredUser";
            }
            if (account.getRoleID().getRoleID() == MantechConstants.ROLE_TECHNICIAN) {
                return "technician";
            }
        }
        session.setAttribute("userSession", null);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Invalid Username or Password!", "Please check again input Username and Password!"));
        return "index";
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);

        if (session != null) {
            session.removeAttribute("userSession");
            session.invalidate();
        }
        return "index";
    }

    public String changeUserPassword() {
        return "changeUserPassword";
    }

    public String newAccount() {
        curUser = new UserAccount(userAccountFacade.getNextAccountID());
        curUser.setStatus(true);
        return "newAccount";
    }

    public String viewAccounts() {
        return "viewAccounts";
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
            return "index";
        }

        if (!newPassword.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Confirm password does not match with New password!",
                    "Please enter again your Confirm password!"));
            return "changeUserPassword";
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
        return "changeUserPassword";
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

}
