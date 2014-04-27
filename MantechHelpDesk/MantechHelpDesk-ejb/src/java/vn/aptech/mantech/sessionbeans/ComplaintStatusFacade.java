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
import vn.aptech.mantech.entity.ComplaintStatus;

/**
 *
 * @author TruongLQ
 */
@Stateless
public class ComplaintStatusFacade extends AbstractFacade<ComplaintStatus> implements ComplaintStatusFacadeLocal {
    @PersistenceContext(unitName = "MantechHelpDesk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComplaintStatusFacade() {
        super(ComplaintStatus.class);
    }

    @Override
    public List<ComplaintStatus> getAllStatusExceptPendings() {
        Query query = em.createQuery("SELECT cs from ComplaintStatus cs where cs.statusID <> 1");
        return query.getResultList();
    }
    
}
