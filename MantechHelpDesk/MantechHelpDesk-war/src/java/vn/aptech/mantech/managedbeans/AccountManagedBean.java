/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.sessionbeans.UserAccountFacadeLocal;

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
    private static final int ROLE_ADMIN = 1;
    private static final int ROLE_USER = 3;
    private static final int ROLE_TECHNICIAN = 2;

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
        if (account != null && account.getPassword().equals(password)) {
            session.setAttribute("userSession", account);
            if (account.getRoleID().getRoleID() == ROLE_ADMIN) {
                return "administrator";
            }
            if (account.getRoleID().getRoleID() == ROLE_USER) {
                return "registeredUser";
            }
            if (account.getRoleID().getRoleID() == ROLE_TECHNICIAN) {
                return "technician";
            }
        }
        session.setAttribute("userSession", null);
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Invalid Username or Password!","Please check again input Username and Password!"));
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
}
