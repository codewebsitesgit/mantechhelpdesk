/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.mantech.managedbeans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.session.bean.UserAccountFacadeLocal;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "account")
@SessionScoped
public class AccountManagedBean {

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
        if (account != null) {
            if (account.getPassword().equals(password)) {
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
        }
        return "loginFailed";
    }
}
