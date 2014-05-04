/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import vn.aptech.mantech.utils.RolesUtils;

/**
 *
 * @author TruongLQ
 */
public class SecurityRole implements Principal, Serializable{
    private final String name;
    private final List<String> permissionPages;
    
    @Override
    public String getName() {
        return name;
    }
    
    public SecurityRole(String name) {
        this.name = name;
        this.permissionPages = RolesUtils.getUserPages(name);
    }
    
    public List<String> getPermissionPages() {
        return permissionPages;
    }
    
    public boolean hasPermission(String roleName, String page) {
        if (!roleName.equals(name)) {
            return false;
        }
        return permissionPages.contains(page);
    }
}
