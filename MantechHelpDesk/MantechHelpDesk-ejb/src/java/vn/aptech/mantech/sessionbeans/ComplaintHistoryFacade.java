/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import vn.aptech.mantech.entity.ComplaintHistory;

/**
 *
 * @author TruongLQ
 */
@Stateless
public class ComplaintHistoryFacade extends AbstractFacade<ComplaintHistory> implements ComplaintHistoryFacadeLocal {
    @PersistenceContext(unitName = "MantechHelpDesk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComplaintHistoryFacade() {
        super(ComplaintHistory.class);
    }
    
}
