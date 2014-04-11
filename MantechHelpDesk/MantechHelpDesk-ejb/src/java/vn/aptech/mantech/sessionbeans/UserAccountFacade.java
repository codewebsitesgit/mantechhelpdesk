/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public UserAccount getUserAccount(String username) {
        Query query = em.createQuery("SELECT u from UserAccount u WHERE u.username=:userName");
        query.setParameter("userName", username);
        return (UserAccount)query.getSingleResult();
    }
}
