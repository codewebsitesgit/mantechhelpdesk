/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import vn.aptech.mantech.entity.Complaint;

/**
 *
 * @author TruongLQ
 */
@SuppressWarnings("unchecked")
@Stateless
public class ComplaintFacade extends AbstractFacade<Complaint> implements ComplaintFacadeLocal {
    @PersistenceContext(unitName = "MantechHelpDesk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComplaintFacade() {
        super(Complaint.class);
    }

    @Override
    public int getMaxComplaintID() {
        Query query = em.createQuery("SELECT MAX(q.complaintID) from Complaint q");
        Object obj= query.getSingleResult();
        if (obj == null) {
            return 1;
        }
        return Integer.parseInt(obj.toString()) + 1;
    }

    @Override
    public List<Complaint> findAllLatest() {
        Query query = em.createQuery("SELECT q from Complaint q ORDER BY q.lodgingDate DESC");
        return query.getResultList();
    }

    @Override
    public List<Complaint> findTopFiveLatest() {
        Query query = em.createQuery("SELECT q from Complaint q ORDER BY q.lodgingDate DESC");
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public List<Complaint> getAllSearchedComplaints(Integer complainID,
            String subject, Date creationDate, Integer statusID) {
        String sql = "SELECT q from Complaint q WHERE 1=1";
        if (complainID != null && complainID != 0) {
            sql += " AND q.complaintID=:compID";
        }
        if (subject != null && !subject.isEmpty()) {
            sql += " AND q.complaintSubject LIKE :compSubject";
        }
        if (creationDate != null) {
            sql += " AND q.lodgingDate=:creationDate";
        }
        if (statusID != null) {
            sql += " AND q.status.statusID=:sttID";
        }
        sql += " ORDER BY q.lodgingDate DESC";
        Query query = em.createQuery(sql);
        if (complainID != null && complainID != 0) {
            query.setParameter("compID", complainID);
        }
        if (subject != null && !subject.isEmpty()) {
            query.setParameter("compSubject", "%"+subject + "%");
        }
        if (creationDate != null) {
            query.setParameter("creationDate", creationDate);
        }
        if (statusID != null) {
            query.setParameter("sttID", statusID);
        }
        return query.getResultList();
    }
    
}
