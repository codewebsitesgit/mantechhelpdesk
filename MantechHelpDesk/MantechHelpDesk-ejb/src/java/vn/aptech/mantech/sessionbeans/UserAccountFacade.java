/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import vn.aptech.mantech.constants.MantechConstants;
import vn.aptech.mantech.entity.UserAccount;

/**
 *
 * @author TruongLQ
 */
@Stateless
public class UserAccountFacade extends AbstractFacade<UserAccount> implements UserAccountFacadeLocal {
    @PersistenceContext(unitName = "MantechHelpDesk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserAccountFacade() {
        super(UserAccount.class);
    }
    
    @Override
    public int getNextAccountID() {
        Query query = em.createQuery("SELECT MAX(u.accountID) from UserAccount u");
        Object obj= query.getSingleResult();
        if (obj == null) {
            return 1;
        }
        return Integer.parseInt(obj.toString()) + 1;
    }
    
    @Override
    public UserAccount getUserAccount(String username) {
        Query query = em.createQuery("SELECT u from UserAccount u WHERE u.username=:userName");
        query.setParameter("userName", username);
        try {
            return (UserAccount)query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<UserAccount> getAllTechnicians() {
        Query query = em.createQuery("SELECT u from UserAccount u WHERE u.roleID.roleID =:userRole");
        query.setParameter("userRole", MantechConstants.ROLE_TECHNICIAN);
        return query.getResultList();
    }

    @Override
    public List<UserAccount> getAllAccount(Integer accountID, String username, Integer departmentID, String fullName) {
        String sql = "SELECT a from UserAccount a WHERE a.roleID.roleID <> 1";
        if (accountID != null && accountID != 0) {
            sql += " AND a.accountID=:accountID";
        }
        if (username != null && !username.isEmpty()) {
            sql += " AND a.username =:username";
        }
        
        if (departmentID != null) {
            sql += " AND a.departmentID.departmentID=:departmentID";
        }
        if (fullName != null && !fullName.isEmpty()) {
            sql += " AND a.name LIKE :fullName";
        }
        Query query = em.createQuery(sql);
        //query.setParameter("account", accountID);
        if (accountID != null && accountID != 0) {
            query.setParameter("accountID", accountID);
        }
        if (username != null && !username.isEmpty()) {
            query.setParameter("username", username);
        }
        
        if (departmentID != null) {
            query.setParameter("departmentID", departmentID);
        }
        
        if (fullName != null && !fullName.isEmpty()) {
            query.setParameter("fullName", "%"+fullName+"%");
        }
        return query.getResultList();
    }
    
    @Override
    public List<UserAccount> getAllAccountWithAdmin(Integer accountID, String username, Integer departmentID, String fullName) {
        String sql = "SELECT a from UserAccount a WHERE 1=1";
        if (accountID != null && accountID != 0) {
            sql += " AND a.accountID=:accountID";
        }
        if (username != null && !username.isEmpty()) {
            sql += " AND a.username =:username";
        }
        
        if (departmentID != null) {
            sql += " AND a.departmentID.departmentID=:departmentID";
        }
        if (fullName != null && !fullName.isEmpty()) {
            sql += " AND a.name LIKE :fullName";
        }
        Query query = em.createQuery(sql);
        //query.setParameter("account", accountID);
        if (accountID != null && accountID != 0) {
            query.setParameter("accountID", accountID);
        }
        if (username != null && !username.isEmpty()) {
            query.setParameter("username", username);
        }
        
        if (departmentID != null) {
            query.setParameter("departmentID", departmentID);
        }
        
        if (fullName != null && !fullName.isEmpty()) {
            query.setParameter("fullName", "%"+fullName+"%");
        }
        return query.getResultList();
    }
    
}
