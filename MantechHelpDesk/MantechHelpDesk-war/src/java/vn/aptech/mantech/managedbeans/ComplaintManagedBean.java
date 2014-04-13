/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import vn.aptech.mantech.entity.Activity;
import vn.aptech.mantech.entity.Complaint;
import vn.aptech.mantech.entity.ComplaintCategory;
import vn.aptech.mantech.entity.ComplaintHistory;
import vn.aptech.mantech.entity.ComplaintPriority;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.sessionbeans.ActivityFacadeLocal;
import vn.aptech.mantech.sessionbeans.ComplaintCategoryFacadeLocal;
import vn.aptech.mantech.sessionbeans.ComplaintFacadeLocal;
import vn.aptech.mantech.sessionbeans.ComplaintHistoryFacadeLocal;
import vn.aptech.mantech.sessionbeans.ComplaintPriorityFacadeLocal;
import vn.aptech.mantech.sessionbeans.ComplaintStatusFacadeLocal;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "complaint")
@SessionScoped
public class ComplaintManagedBean implements Serializable {

    @EJB
    private ActivityFacadeLocal activityFacade;
    @EJB
    private ComplaintHistoryFacadeLocal complaintHistoryFacade;

    @EJB
    private ComplaintStatusFacadeLocal complaintStatusFacade;
    @EJB
    private ComplaintPriorityFacadeLocal complaintPriorityFacade;
    @EJB
    private ComplaintFacadeLocal complaintFacade;
    @EJB
    private ComplaintCategoryFacadeLocal complaintCategoryFacade;

    private Complaint curComplaint;

    private int complaintPriority;

    private int complaintCategory;
    private static final int PRIORITY_NORMAL = 2;

    @Resource
    UserTransaction ut;
    private static final int PENDING_STATUS = 1;

    private Integer searchedComplaintID = null;
    private String searchedSubject;
    private Date creationDate;
    private Integer statusID = null;

    private int resentComplaintId;

    private Complaint adminComplaintDetail;

    public Complaint getAdminComplaintDetail() {
        return adminComplaintDetail;
    }

    public void setAdminComplaintDetail(Complaint adminComplaintDetail) {
        this.adminComplaintDetail = adminComplaintDetail;
    }
    
    public int getResentComplaintId() {
        return resentComplaintId;
    }

    public void setResentComplaintId(int resentComplaintId) {
        this.resentComplaintId = resentComplaintId;
    }

    /**
     * Creates a new instance of ComplaintManagedBean
     */
    public ComplaintManagedBean() {
    }

    public String newComplaint() {
        setUpNewComplaint();
        return "newComplaint";
    }

    private void setUpNewComplaint() {
        final Complaint cmpl = new Complaint();
        cmpl.setComplaintID(complaintFacade.getMaxComplaintID());
        setComplaintPriority(PRIORITY_NORMAL);
        setCurComplaint(cmpl);
    }

    public String viewComplaint() {
        return "viewComplaint";
    }

    /**
     * @return the curComplaint
     */
    public Complaint getCurComplaint() {
        return curComplaint;
    }

    /**
     * @param curComplaint the curComplaint to set
     */
    public void setCurComplaint(Complaint curComplaint) {
        this.curComplaint = curComplaint;
    }

    public List<ComplaintCategory> getComplaintCats() {
        return complaintCategoryFacade.findAll();
    }

    public String sendComplaint() {
        try {
            ut.begin();
            ComplaintCategory category = complaintCategoryFacade.find(complaintCategory);
            ComplaintPriority priority = complaintPriorityFacade.find(complaintPriority);
            curComplaint.setComplaintCategory(category);
            curComplaint.setPriority(priority);
            curComplaint.setLodgingDate(Calendar.getInstance().getTime());
            curComplaint.setStatus(complaintStatusFacade.find(PENDING_STATUS));
            curComplaint.setComplaintOwner(getSessionUserAccount());
            
            complaintFacade.create(curComplaint);
            // save to history table
            UserAccount user = getSessionUserAccount();
            ComplaintHistory hist = new ComplaintHistory();
            hist.setHistoryID(complaintHistoryFacade.getMaxHistoryID());
            Activity newComplaintAction = activityFacade.getNewComplaint();
            hist.setActionID(newComplaintAction);
            hist.setDetails(newComplaintAction.getActionDesc());
            hist.setUserAccountID(user);
            hist.setLastModifiedDate(Calendar.getInstance().getTime());
            
            // find history for complaint
            Complaint cmp = complaintFacade.find(curComplaint.getComplaintID());
            hist.setComplaintID(cmp);
            complaintHistoryFacade.create(hist);
            
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return newComplaint();
    }

    private static UserAccount getSessionUserAccount() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        return (UserAccount) session.getAttribute("userSession");
    }

    public List<Complaint> getAllComplaints() {
        return complaintFacade.findAllLatest();
    }

    public List<Complaint> getTopFiveComplaints() {
        return complaintFacade.findTopFiveLatest();
    }

    /**
     * @return the complaintPriority
     */
    public int getComplaintPriority() {
        return complaintPriority;
    }

    /**
     * @param complaintPriority the complaintPriority to set
     */
    public void setComplaintPriority(int complaintPriority) {
        this.complaintPriority = complaintPriority;
    }

    /**
     * @return the complaintCategory
     */
    public int getComplaintCategory() {
        return complaintCategory;
    }

    /**
     * @param complaintCategory the complaintCategory to set
     */
    public void setComplaintCategory(int complaintCategory) {
        this.complaintCategory = complaintCategory;
    }

    /**
     * @return the searchedComplaintID
     */
    public Integer getSearchedComplaintID() {
        return searchedComplaintID;
    }

    /**
     * @param searchedComplaintID the searchedComplaintID to set
     */
    public void setSearchedComplaintID(Integer searchedComplaintID) {
        this.searchedComplaintID = searchedComplaintID;
    }

    /**
     * @return the searchedSubject
     */
    public String getSearchedSubject() {
        return searchedSubject;
    }

    /**
     * @param searchedSubject the searchedSubject to set
     */
    public void setSearchedSubject(String searchedSubject) {
        this.searchedSubject = searchedSubject;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the statusID
     */
    public Integer getStatusID() {
        return statusID;
    }

    /**
     * @param statusID the statusID to set
     */
    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    /**
     * @return the allSearchedComplaints
     */
    public List<Complaint> getAllSearchedComplaints() {
        List<Complaint> allComplaints = complaintFacade.getAllSearchedComplaints(searchedComplaintID, searchedSubject, null, statusID);
        if (allComplaints != null && !allComplaints.isEmpty()) {
            for (Complaint cp : allComplaints) {
                if (cp.getStatus().getStatusID() == 1) {
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    long loggingDate = cp.getLodgingDate().getTime();
                    long distance = currentTime - loggingDate;
                    boolean resent = distance >= 2 * 24 * 3600 * 1000; //greater than 2 days
                    cp.setResend(resent);
                }
            }
        }
        return allComplaints;
    }

    public String resend(int complaintID) {
        System.out.println("Resend: " + complaintID);
        return "viewComplaint";
    }

    public String viewComplaintHistory() {
        return "viewComplaintHistory";
    }

    public String viewComplaintAssignment() {
        return "viewComplaintAssignment";
    }
    
    public String viewComplaintDetail() {
        return "viewComplaintDetail";
    }
}
