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

    @Override
    public int getMaxHistoryID() {
        Query query = em.createQuery("SELECT MAX(ch.historyID) from ComplaintHistory ch");
        Object obj= query.getSingleResult();
        if (obj == null) {
            return 1;
        }
        return Integer.parseInt(obj.toString()) + 1;
    }

    @Override
    public List<ComplaintHistory> getAllSortedComplaintHistories() {
        Query query = em.createQuery("SELECT ch from ComplaintHistory ch ORDER BY ch.lastModifiedDate DESC");
        return query.getResultList();
    }
    
    
}
