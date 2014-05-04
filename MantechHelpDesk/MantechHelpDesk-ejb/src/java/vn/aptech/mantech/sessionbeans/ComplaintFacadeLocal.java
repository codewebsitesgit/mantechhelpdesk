/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.Complaint;
import vn.aptech.mantech.entity.ComplaintImages;

/**
 *
 * @author TruongLQ
 */
@Local
public interface ComplaintFacadeLocal {

    void create(Complaint complaint);

    void edit(Complaint complaint);

    void remove(Complaint complaint);

    Complaint find(Object id);

    List<Complaint> findAll();

    List<Complaint> findRange(int[] range);

    int count();

    int getMaxComplaintID();

    List<Complaint> findAllLatest();

    List<Complaint> findTopFiveLatest(int accountID);

    List<Complaint> getAllSearchedComplaints(
            Integer complainID, String subject, 
            Date creationDate, int statusID, 
            int accountID);

    List<Complaint> getAllTechnicianAssignments(int accountID);

    List<Complaint> getLastModifiedComplaints();

    List<Complaint> getDailyComplaints();

    List<Complaint> getWeeklyComplaints();

    List<Complaint> getMonthlyComplaints();

    List<Complaint> getPendingComplaints();

    List<Complaint> getAllDepartmentWiseReport(int departmentID);

    List<Complaint> getAllTechnicianWiseReport(int technicianID);

    List<Complaint> getAllCategoryWiseReport(int categoryID);

    int getComplaintImageMaxID();

    List<ComplaintImages> getAllComplaintImages(int complaintID);

}
