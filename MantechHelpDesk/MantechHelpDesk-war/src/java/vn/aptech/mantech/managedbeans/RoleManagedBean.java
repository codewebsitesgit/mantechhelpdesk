/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import vn.aptech.mantech.entity.UserRole;
import vn.aptech.mantech.sessionbeans.UserRoleFacadeLocal;

/**
 *
 * @author KimHao
 */
@ManagedBean(name = "role")
@SessionScoped
public class RoleManagedBean implements Serializable{

    @EJB
    private UserRoleFacadeLocal userRoleFacade;

    /**
     * Creates a new instance of RoleManagedBean
     */
    public RoleManagedBean() {
    }

    public List<UserRole> getAllRole() {
        List<UserRole> roles = userRoleFacade.findAll();
        roles.remove(userRoleFacade.find(1));
        return roles;
    }

}
