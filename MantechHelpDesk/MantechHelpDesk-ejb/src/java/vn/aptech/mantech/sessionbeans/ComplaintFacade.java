/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import vn.aptech.mantech.entity.Complaint;
import vn.aptech.mantech.entity.ComplaintImages;

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
    public List<Complaint> findTopFiveLatest(int accountID) {
        Query query = em.createQuery("SELECT q from Complaint q WHERE q.complaintOwner.accountID =:account ORDER BY q.lodgingDate DESC");
        query.setParameter("account", accountID);
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public List<Complaint> getAllSearchedComplaints(Integer complainID,
            String subject, Date creationDate, Integer statusID, int accountID) {
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
        sql += " AND q.complaintOwner.accountID =:account";
        sql += " ORDER BY q.lodgingDate DESC";
        Query query = em.createQuery(sql);
        query.setParameter("account", accountID);
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

    @Override
    public List<Complaint> getAllTechnicianAssignments(int accountID) {
        Query query = em.createQuery("SELECT q from Complaint q WHERE q.technician.accountID=:account ORDER BY q.lodgingDate DESC");
        query.setParameter("account", accountID);
        return query.getResultList();
    }

    @Override
    public List<Complaint> getLastModifiedComplaints() {
        Query query = em.createQuery("SELECT q from Complaint q ORDER BY q.lastModified DESC");
        return query.getResultList();
    }

    @Override
    public List<Complaint> getDailyComplaints() {
        Query query = em.createQuery("SELECT cp from Complaint cp WHERE YEAR(cp.lodgingDate) =:years AND "
                + "MONTH(cp.lodgingDate) =:months AND DAY(cp.lodgingDate) =:days ORDER BY cp.lastModified DESC");
        query.setParameter("years", Calendar.getInstance().get(Calendar.YEAR));
        query.setParameter("months", Calendar.getInstance().get(Calendar.MONTH) + 1);
        query.setParameter("days", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return query.getResultList();
    }

    @Override
    public List<Complaint> getWeeklyComplaints() {
        Query query = em.createQuery("SELECT cp from Complaint cp WHERE YEAR(cp.lodgingDate) =:years AND "
                + "MONTH(cp.lodgingDate) =:months AND EXTRACT(WEEK from cp.lodgingDate) =:weeks ORDER BY cp.lastModified DESC");
        query.setParameter("years", Calendar.getInstance().get(Calendar.YEAR));
        query.setParameter("months", Calendar.getInstance().get(Calendar.MONTH)+ 1);
        query.setParameter("weeks", Calendar.getInstance().get(Calendar.WEEK_OF_YEAR));
        return query.getResultList();
    }

    @Override
    public List<Complaint> getMonthlyComplaints() {
        Query query = em.createQuery("SELECT cp from Complaint cp WHERE YEAR(cp.lodgingDate) =:years AND MONTH(cp.lodgingDate) =:months ORDER BY cp.lastModified DESC");
        query.setParameter("years", Calendar.getInstance().get(Calendar.YEAR));
        query.setParameter("months", Calendar.getInstance().get(Calendar.MONTH) + 1);
        return query.getResultList();
    }

    @Override
    public List<Complaint> getPendingComplaints() {
        Query query = em.createQuery("SELECT q from Complaint q WHERE q.status.statusID = 1 ORDER BY q.lastModified DESC");
        return query.getResultList();
    }

    @Override
    public List<Complaint> getAllDepartmentWiseReport(int departmentID) {
        Query query = em.createQuery("SELECT q from Complaint q WHERE q.complaintOwner.departmentID.departmentID =:departID ORDER BY q.lastModified DESC");
        query.setParameter("departID", departmentID);
        return query.getResultList();
    }

    @Override
    public List<Complaint> getAllTechnicianWiseReport(int technicianID) {
        Query query = em.createQuery("SELECT q from Complaint q WHERE q.technician.accountID =:techID ORDER BY q.lastModified DESC");
        query.setParameter("techID", technicianID);
        return query.getResultList();
    }

    @Override
    public List<Complaint> getAllCategoryWiseReport(int categoryID) {
        Query query = em.createQuery("SELECT q from Complaint q WHERE q.complaintCategory.categoryID =:catID ORDER BY q.lastModified DESC");
        query.setParameter("catID", categoryID);
        return query.getResultList();
    }

    @Override
    public int getComplaintImageMaxID() {
        Query query = em.createQuery("SELECT MAX(q.complaintImageID) from ComplaintImages q");
        Object obj= query.getSingleResult();
        if (obj == null) {
            return 1;
        }
        return Integer.parseInt(obj.toString()) + 1;
    }

    @Override
    public List<ComplaintImages> getAllComplaintImages(int complaintID) {
        Query query = em.createQuery("SELECT ci from ComplaintImages ci WHERE ci.complaintID.complaintID =:cmpID");
        query.setParameter("cmpID", complaintID);
        return query.getResultList();
    }
    
    
}
