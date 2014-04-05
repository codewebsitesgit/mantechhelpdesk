/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.session.bean;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.UserRole;

/**
 *
 * @author TruongLQ
 */
@Local
public interface UserRoleFacadeLocal {

    void create(UserRole userRole);

    void edit(UserRole userRole);

    void remove(UserRole userRole);

    UserRole find(Object id);

    List<UserRole> findAll();

    List<UserRole> findRange(int[] range);

    int count();
    
}
