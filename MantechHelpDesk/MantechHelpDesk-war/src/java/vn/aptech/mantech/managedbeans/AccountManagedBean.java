/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "account")
@SessionScoped
public class AccountManagedBean {

    private String username;
    private String password;
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
        if ("admin".equals(username) && "admin".equals(password)) {
            return "administrator";
        }
        if ("user".equals(username) && "user".equals(password)) {
            return "registeredUser";
        }
        return "loginFailed";
    }
}