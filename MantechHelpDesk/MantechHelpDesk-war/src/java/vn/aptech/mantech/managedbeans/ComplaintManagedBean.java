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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import vn.aptech.mantech.constants.MantechConstants;
import vn.aptech.mantech.entity.Activity;
import vn.aptech.mantech.entity.Complaint;
import vn.aptech.mantech.entity.ComplaintCategory;
import vn.aptech.mantech.entity.ComplaintHistory;
import vn.aptech.mantech.entity.ComplaintPriority;
import vn.aptech.mantech.entity.ComplaintStatus;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.reports.ReportKind;
import vn.aptech.mantech.sessionbeans.ActivityFacadeLocal;
import vn.aptech.mantech.sessionbeans.ComplaintCategoryFacadeLocal;
import vn.aptech.mantech.sessionbeans.ComplaintFacadeLocal;
import vn.aptech.mantech.sessionbeans.ComplaintHistoryFacadeLocal;
import vn.aptech.mantech.sessionbeans.ComplaintPriorityFacadeLocal;
import vn.aptech.mantech.sessionbeans.ComplaintStatusFacadeLocal;
import vn.aptech.mantech.sessionbeans.UserAccountFacadeLocal;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "complaint")
@SessionScoped
public class ComplaintManagedBean implements Serializable {

    @EJB
    private UserAccountFacadeLocal userAccountFacade;

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

    private Integer complaintCategory;

    @Resource
    UserTransaction ut;

    private Integer searchedComplaintID = null;
    private String searchedSubject;
    private Date creationDate;
    private Integer statusID = null;

    private Complaint adminComplaintDetail;

    private Integer adminSelectedTechnician;
    private int adminSelectedStatusId;
    private int adminSelectedPriorityId;

    private Complaint techComplaintDetail;

    private int techSelectedStatusId;
    private int techSelectedCategory;
    private String techInputReasons;
    
    private int selectedKindOfReport;
    
    private int resendComplaintId;

    public List<ReportKind> getAllKindsOfReport() {
        List<ReportKind> kindsOfReport = new ArrayList<ReportKind>();
        kindsOfReport.add(new ReportKind(ReportKind.DAILY_WEEKLY_MONTHY_REPORT, 
                ReportKind.DAILY_WEEKLY_MONTHY_REPORT_DESC));
        
        kindsOfReport.add(new ReportKind(ReportKind.PENDING_COMPLAINT_REPORT, 
                ReportKind.PENDING_COMPLAINT_REPORT_DESC));
        
        kindsOfReport.add(new ReportKind(ReportKind.DEPARTMENT_WISE_REPORT, 
                ReportKind.DEPARTMENT_WISE_REPORT_DESC));
        
        kindsOfReport.add(new ReportKind(ReportKind.TECHNICIAN_WISE_REPORT, 
                ReportKind.TECHNICIAN_WISE_REPORT_DESC));
        
        kindsOfReport.add(new ReportKind(ReportKind.COMPLAINT_CATEGORY_WISE_REPORT, 
                ReportKind.COMPLAINT_CATEGORY_WISE_REPORT_DESC));
        return kindsOfReport;
    }
    
    public List<Complaint> getAllKindReportComplaints() {
        List<Complaint> allComplaints = complaintFacade.findAll();
        return allComplaints;
    }
    
    public List<Complaint> getDailyWeeklyMonthlySummary() {
        List<Complaint> allComplaints = complaintFacade.findAll();
        return allComplaints;
    }
    
    public List<Complaint> getDailyWeeklyMonthlyDetail() {
        List<Complaint> allComplaints = complaintFacade.findAll();
        return allComplaints;
    }
    
    public String previewReport() {
        return "generateReports";
    }
    
    public int getResendComplaintId() {
        return resendComplaintId;
    }

    public void setResendComplaintId(int resendComplaintId) {
        this.resendComplaintId = resendComplaintId;
    }

    public int getTechSelectedStatusId() {
        return techSelectedStatusId;
    }

    public void setTechSelectedStatusId(int techSelectedStatusId) {
        this.techSelectedStatusId = techSelectedStatusId;
    }

    public int getTechSelectedCategory() {
        return techSelectedCategory;
    }

    public void setTechSelectedCategory(int techSelectedCategory) {
        this.techSelectedCategory = techSelectedCategory;
    }

    public String getTechInputReasons() {
        return techInputReasons;
    }

    public void setTechInputReasons(String techInputReasons) {
        this.techInputReasons = techInputReasons;
    }

    public Complaint getTechComplaintDetail() {
        return techComplaintDetail;
    }

    public void setTechComplaintDetail(Complaint techComplaintDetail) {
        this.techComplaintDetail = techComplaintDetail;
    }

    public Integer getAdminSelectedTechnician() {
        return adminSelectedTechnician;
    }

    public void setAdminSelectedTechnician(Integer adminSelectedTechnician) {
        this.adminSelectedTechnician = adminSelectedTechnician;
    }

    public int getAdminSelectedStatusId() {
        return adminSelectedStatusId;
    }

    public void setAdminSelectedStatusId(int adminSelectedStatusId) {
        this.adminSelectedStatusId = adminSelectedStatusId;
    }

    public int getAdminSelectedPriorityId() {
        return adminSelectedPriorityId;
    }

    public void setAdminSelectedPriorityId(int adminSelectedPriorityId) {
        this.adminSelectedPriorityId = adminSelectedPriorityId;
    }

    public Complaint getAdminComplaintDetail() {
        return adminComplaintDetail;
    }

    public void setAdminComplaintDetail(Complaint adminComplaintDetail) {
        this.adminComplaintDetail = adminComplaintDetail;
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
        setComplaintPriority(MantechConstants.COMPLAINT_PRIORITY_NORMAL);
        setCurComplaint(cmpl);
    }

    public String viewComplaint() {
        searchedComplaintID = null;
        searchedSubject = null;
        statusID = null;
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
            ComplaintCategory category = complaintCategoryFacade.find(complaintCategory.intValue());
            ComplaintPriority priority = complaintPriorityFacade.find(complaintPriority);
            curComplaint.setComplaintCategory(category);
            curComplaint.setPriority(priority);
            Date modifiedDate = Calendar.getInstance().getTime();
            curComplaint.setLodgingDate(modifiedDate);
            curComplaint.setStatus(complaintStatusFacade.find(MantechConstants.COMPLAINT_STATUS_PENDING));
            curComplaint.setComplaintOwner(getSessionUserAccount());
            curComplaint.setLastModified(modifiedDate);
            complaintFacade.create(curComplaint);
            // save to history table
            UserAccount user = getSessionUserAccount();
            ComplaintHistory hist = new ComplaintHistory();
            hist.setHistoryID(complaintHistoryFacade.getMaxHistoryID());
            Activity newComplaintAction = activityFacade.getNewComplaint();
            hist.setActionID(newComplaintAction);
            hist.setDetails(newComplaintAction.getActionDesc());
            hist.setUserAccountID(user);
            hist.setLastModifiedDate(modifiedDate);

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
        return complaintFacade.findTopFiveLatest(
                getSessionUserAccount().getAccountID());
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
    public Integer getComplaintCategory() {
        return complaintCategory;
    }

    /**
     * @param complaintCategory the complaintCategory to set
     */
    public void setComplaintCategory(Integer complaintCategory) {
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
        List<Complaint> allComplaints = complaintFacade.getAllSearchedComplaints(
                searchedComplaintID, searchedSubject, null, statusID,
                getSessionUserAccount().getAccountID());
        if (allComplaints != null && !allComplaints.isEmpty()) {
            for (Complaint cp : allComplaints) {
                //in milliseconds
                if (cp.getStatus().getStatusID() == MantechConstants.COMPLAINT_STATUS_PENDING) {
                    long diffResend = Calendar.getInstance().getTimeInMillis() - cp.getLastModified().getTime();
                    long diffDays = diffResend / (24 * 60 * 60 * 1000);
                    cp.setResend(diffDays >= 2L); //greater than 2 days
                } else if (cp.getStatus().getStatusID() == MantechConstants.COMPLAINT_STATUS_DONE) {
                    long diffActualTaken = cp.getClosingDate().getTime() - cp.getLodgingDate().getTime();
                    long diffSeconds = diffActualTaken / 1000 % 60;
                    long diffMinutes = diffActualTaken / (60 * 1000) % 60;
                    long diffHours = diffActualTaken / (60 * 60 * 1000) % 24;
                    long diffDays = diffActualTaken / (24 * 60 * 60 * 1000);
                    cp.setActualTakenDays(diffDays);
                    cp.setActualTakenHours(diffHours);
                    cp.setActualTakenMinutes(diffMinutes);
                    cp.setActualTakenSeconds(diffSeconds);
                }
            }
        }
        return allComplaints;
    }

    public String resendComplaintItem() {
        Complaint cmp = complaintFacade.find(resendComplaintId);
        cmp.setLastModified(Calendar.getInstance().getTime());
        complaintFacade.edit(cmp);
        
        // save history
        updateHistory(cmp, activityFacade.getResendComplaint());
        return "viewComplaint";
    }

    public String viewComplaintHistory() {
        return "viewComplaintHistory";
    }

    public String viewComplaintAssignment() {
        return "viewComplaintAssignment";
    }

    public String viewComplaintDetail() {
        adminSelectedPriorityId = adminComplaintDetail.getPriority().getPriorityID();
        adminSelectedStatusId = adminComplaintDetail.getStatus().getStatusID();
        UserAccount tech = adminComplaintDetail.getTechnician();
        if (tech != null) {
            adminSelectedTechnician = tech.getAccountID();
        }

        return "viewComplaintDetail";
    }

    public String viewTechComplaintDetail() {
        techInputReasons = techComplaintDetail.getReasons();
        techSelectedCategory = techComplaintDetail.getComplaintCategory().getCategoryID();
        techSelectedStatusId = techComplaintDetail.getStatus().getStatusID();
        return "viewTechComplaintDetail";
    }

    public String updateAdminComplaintDetail() {
        boolean hasChangedTech = checkChangeTech();
        boolean hasChangedStatus = checkChangeStatus();
        boolean hasChangedPriority = checkChangePriority();

        if (!hasChangedTech && !hasChangedStatus && !hasChangedPriority) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "No changes in any field to update!", "Please modify at least one field to update!"));
            return "viewComplaintDetail";
        }

        try {
            ut.begin();
            // get technician
            // check if there's change in technician

            if (adminSelectedTechnician == null) {
                adminComplaintDetail.setTechnician(null);
            } else {
                UserAccount tech = userAccountFacade.find(adminSelectedTechnician);
                adminComplaintDetail.setTechnician(tech);
            }

            // update history
            // get status
            // check if there's change in status
            Date modifiedDate = Calendar.getInstance().getTime();
            if (hasChangedStatus) {
                ComplaintStatus status = complaintStatusFacade.find(adminSelectedStatusId);
                if (adminSelectedStatusId == MantechConstants.COMPLAINT_STATUS_DONE) {
                    adminComplaintDetail.setClosingDate(modifiedDate);
                } else {
                    if (adminComplaintDetail.getStatus().getStatusID().intValue() == MantechConstants.COMPLAINT_STATUS_DONE) {
                        // select back to not done
                        adminComplaintDetail.setClosingDate(null);
                    }
                }
                adminComplaintDetail.setStatus(status);
            }
            // get priority
            // check if there's change in priority

            if (hasChangedPriority) {
                ComplaintPriority prio = complaintPriorityFacade.find(adminSelectedPriorityId);
                adminComplaintDetail.setPriority(prio);
            }
            adminComplaintDetail.setLastModified(modifiedDate);
            complaintFacade.edit(adminComplaintDetail);

            Complaint savedComplaint = complaintFacade.find(adminComplaintDetail.getComplaintID());

            // update histories
            if (hasChangedTech) {
                updateHistory(savedComplaint, activityFacade.getAssignTechnician());
            }

            if (hasChangedStatus) {
                updateHistory(savedComplaint, activityFacade.getChangeStatus());
            }

            if (hasChangedPriority) {
                updateHistory(savedComplaint, activityFacade.getChangePriority());
            }

            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return "administrator";
    }

    private boolean checkChangeTech() {
        UserAccount curTech = adminComplaintDetail.getTechnician();
        if (curTech != null) {
            if (adminSelectedTechnician == null) {
                return true;
            } else {
                return curTech.getAccountID().intValue() != adminSelectedTechnician.intValue();
            }
        }
        return adminSelectedTechnician != null;

    }

    private boolean checkChangeStatus() {
        ComplaintStatus status = adminComplaintDetail.getStatus();
        if (status != null) {
            return status.getStatusID().intValue() != adminSelectedStatusId;
        }
        return false;
    }

    private boolean checkChangePriority() {
        ComplaintPriority prio = adminComplaintDetail.getPriority();
        if (prio != null) {
            return prio.getPriorityID().intValue() != adminSelectedPriorityId;
        }
        return false;
    }

    public List<Complaint> getTechnicianAssignments() {
        return complaintFacade.getAllTechnicianAssignments(getSessionUserAccount().getAccountID());
    }

    public String updateTechComplaintDetail() {
        boolean hasChangedStatus = checkTechChangeStatus();
        boolean hasChangedCategory = checkTechChangeCategory();
        boolean hasChangedReasons = checkTechChangeReasons();

        if (!hasChangedStatus && !hasChangedCategory && !hasChangedReasons) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "No changes in any field to update!", "Please modify at least one field to update!"));
            return "viewTechComplaintDetail";
        }

        try {
            ut.begin();
            // update history
            // get status
            // check if there's change in status
            Date modifiedDate = Calendar.getInstance().getTime();
            if (hasChangedStatus) {
                ComplaintStatus status = complaintStatusFacade.find(techSelectedStatusId);
                if (techSelectedStatusId == MantechConstants.COMPLAINT_STATUS_DONE) {
                    techComplaintDetail.setClosingDate(modifiedDate);
                } else {
                    if (techComplaintDetail.getStatus().getStatusID().intValue() == MantechConstants.COMPLAINT_STATUS_DONE) {
                        // select back to not done
                        techComplaintDetail.setClosingDate(null);
                    }
                }
                techComplaintDetail.setStatus(status);
            }
            // get category
            // check if there's change in category

            if (hasChangedCategory) {
                ComplaintCategory cat = complaintCategoryFacade.find(techSelectedCategory);
                techComplaintDetail.setComplaintCategory(cat);
            }

            if (hasChangedReasons) {
                techComplaintDetail.setReasons(techInputReasons);
            }
            techComplaintDetail.setLastModified(modifiedDate);
            complaintFacade.edit(techComplaintDetail);

            Complaint savedComplaint = complaintFacade.find(techComplaintDetail.getComplaintID());

            // update histories
            if (hasChangedStatus) {
                updateHistory(savedComplaint, activityFacade.getChangeStatus());
            }

            if (hasChangedCategory) {
                updateHistory(savedComplaint, activityFacade.getChangeCategory());
            }

            if (hasChangedReasons) {
                updateHistory(savedComplaint, activityFacade.getUpdateRootCause());
            }

            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return "technician";
    }

    private boolean checkTechChangeStatus() {
        ComplaintStatus status = techComplaintDetail.getStatus();
        if (status != null) {
            return status.getStatusID().intValue() != techSelectedStatusId;
        }
        return false;
    }

    private boolean checkTechChangeCategory() {
        ComplaintCategory cat = techComplaintDetail.getComplaintCategory();
        if (cat != null) {
            return cat.getCategoryID().intValue() != techSelectedCategory;
        }
        return false;
    }

    private boolean checkTechChangeReasons() {
        String reason = techComplaintDetail.getReasons();
        if (reason != null) {
            return !reason.equals(techInputReasons);
        }

        return techInputReasons != null && !techInputReasons.trim().isEmpty();
    }

    private void updateHistory(Complaint savedComplaint, Activity act) {
        ComplaintHistory hist = new ComplaintHistory();
        hist.setActionID(act);
        hist.setHistoryID(complaintHistoryFacade.getMaxHistoryID());
        hist.setDetails(act.getActionDesc());
        hist.setComplaintID(savedComplaint);
        hist.setLastModifiedDate(Calendar.getInstance().getTime());
        hist.setUserAccountID(getSessionUserAccount());
        complaintHistoryFacade.edit(hist);
    }

    public String viewLastModifiedComplaints() {
        return "viewLastModifiedComplaints";
    }

    public List<Complaint> getAllLatestModifiedComplaints() {
        return complaintFacade.getLastModifiedComplaints();
    }

    public String generateReports() {
        selectedKindOfReport = 0;
        return "generateReports";
    }

    /**
     * @return the selectedKindOfReport
     */
    public int getSelectedKindOfReport() {
        return selectedKindOfReport;
    }

    /**
     * @param selectedKindOfReport the selectedKindOfReport to set
     */
    public void setSelectedKindOfReport(int selectedKindOfReport) {
        this.selectedKindOfReport = selectedKindOfReport;
    }
}
