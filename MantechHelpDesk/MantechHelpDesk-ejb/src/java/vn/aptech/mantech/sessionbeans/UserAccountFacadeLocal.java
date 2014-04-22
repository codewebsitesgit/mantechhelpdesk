/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.UserAccount;

/**
 *
 * @author TruongLQ
 */
@Local
public interface UserAccountFacadeLocal {

    void create(UserAccount userAccount);

    void edit(UserAccount userAccount);

    void remove(UserAccount userAccount);

    UserAccount find(Object id);

    List<UserAccount> findAll();

    List<UserAccount> findRange(int[] range);

    int count();
    
    UserAccount getUserAccount(String username);

    List<UserAccount> getAllTechnicians();
    
    int getNextAccountID();

    List<UserAccount> getAllAccount(Integer accountID, String username, Integer departmentID, String fullName);
}
