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
import vn.aptech.mantech.entity.Department;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.reports.DailyWeeklyMonthlyBean;
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

    private int reportDepartmentWiseId;
    private int reportTechnicianWiseId;
    private int reportCategoryWiseId;
    
    private int subDmwReport;

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
    
    public List<ReportKind> getSubDwmReportKinds() {
        List<ReportKind> subDwmKinds = new ArrayList<ReportKind>();
        subDwmKinds.add(new ReportKind(ReportKind.DAILY_WEEKLY_MONTHY_SUMMARY_REPORT,
                ReportKind.DAILY_WEEKLY_MONTHY_SUMMARY_REPORT_DESC));
        subDwmKinds.add(new ReportKind(ReportKind.DAILY_WEEKLY_MONTHY_DETAIL_REPORT,
                ReportKind.DAILY_WEEKLY_MONTHY_DETAIL_REPORT_DESC));

        return subDwmKinds;
    }

    public List<Complaint> getDailyComplaints() {
        List<Complaint> allComplaints = complaintFacade.getDailyComplaints();
        updateResendAndActualTakenTime(allComplaints);
        return allComplaints;
    }

    public List<Complaint> getWeeklyComplaints() {
        List<Complaint> allComplaints = complaintFacade.getWeeklyComplaints();
        updateResendAndActualTakenTime(allComplaints);
        return allComplaints;
    }

    public List<Complaint> getMonthlyComplaints() {
        List<Complaint> allComplaints = complaintFacade.getMonthlyComplaints();
        updateResendAndActualTakenTime(allComplaints);
        return allComplaints;
    }

    public List<DailyWeeklyMonthlyBean> getDailyWeeklyMonthlySummary() {
        List<Complaint> daily = getDailyComplaints();
        List<Complaint> weekly = getWeeklyComplaints();
        List<Complaint> monthly = getMonthlyComplaints();

        List<DailyWeeklyMonthlyBean> dailySummaryReports = new ArrayList<DailyWeeklyMonthlyBean>();
        int[][] dailyReports = buildDepartmentAndStatus(daily);
        int[][] weeklyReports = buildDepartmentAndStatus(weekly);
        int[][] monthlyReports = buildDepartmentAndStatus(monthly);

        DailyWeeklyMonthlyBean ds = new DailyWeeklyMonthlyBean(
                "Department: Educational Services", dailyReports[0][0], weeklyReports[0][0], monthlyReports[0][0]);
        DailyWeeklyMonthlyBean ds1 = new DailyWeeklyMonthlyBean(
                "Department: Management Services", dailyReports[1][0], weeklyReports[1][0], monthlyReports[1][0]);
        DailyWeeklyMonthlyBean ds2 = new DailyWeeklyMonthlyBean(
                "Department: Learning Services", dailyReports[2][0], weeklyReports[2][0], monthlyReports[2][0]);
        DailyWeeklyMonthlyBean ds3 = new DailyWeeklyMonthlyBean(
                "Department: Internal Systems", dailyReports[3][0], weeklyReports[3][0], monthlyReports[3][0]);
        DailyWeeklyMonthlyBean ds4 = new DailyWeeklyMonthlyBean(
                "Department: Human Resources", dailyReports[4][0], weeklyReports[4][0], monthlyReports[4][0]);
        DailyWeeklyMonthlyBean ds5 = new DailyWeeklyMonthlyBean(
                "Status: Pending", dailyReports[5][0], weeklyReports[5][0], monthlyReports[5][0]);
        DailyWeeklyMonthlyBean ds6 = new DailyWeeklyMonthlyBean(
                "Status: Resolving", dailyReports[6][0], weeklyReports[6][0], monthlyReports[6][0]);
        DailyWeeklyMonthlyBean ds7 = new DailyWeeklyMonthlyBean(
                "Status: Handing", dailyReports[7][0], weeklyReports[7][0], monthlyReports[7][0]);
        DailyWeeklyMonthlyBean ds8 = new DailyWeeklyMonthlyBean(
                "Status: Done", dailyReports[8][0], weeklyReports[8][0], monthlyReports[8][0]);
        DailyWeeklyMonthlyBean b = new DailyWeeklyMonthlyBean(
                "Total complaints No.", daily.size(), weekly.size(), monthly.size());

        dailySummaryReports.add(ds);
        dailySummaryReports.add(ds1);
        dailySummaryReports.add(ds2);
        dailySummaryReports.add(ds3);
        dailySummaryReports.add(ds4);
        dailySummaryReports.add(ds5);
        dailySummaryReports.add(ds6);
        dailySummaryReports.add(ds7);
        dailySummaryReports.add(ds8);
        dailySummaryReports.add(b);

        return dailySummaryReports;
    }

    private int[][] buildDepartmentAndStatus(List<Complaint> daily) {
        int part1 = 0, part2 = 0, part3 = 0, part4 = 0, part5 = 0;
        int status1 = 0, status2 = 0, status3 = 0, status4 = 0;
        int[][] dailyArray = new int[9][1];
        for (Complaint d : daily) {
            Department department = d.getComplaintOwner().getDepartmentID();
            Integer departmentID = department.getDepartmentID();
            ComplaintStatus status = d.getStatus();
            if (MantechConstants.DEPARTMENT_EDU_SERVICE == departmentID.intValue()) {
                part1++;
            }
            if (MantechConstants.DEPARTMENT_MNG_SERVICE == departmentID.intValue()) {
                part2++;
            }
            if (MantechConstants.DEPARTMENT_LRN_SERVICE == departmentID.intValue()) {
                part3++;
            }
            if (MantechConstants.DEPARTMENT_INS_SERVICE == departmentID.intValue()) {
                part4++;
            }
            if (MantechConstants.DEPARTMENT_HRS_SERVICE == departmentID.intValue()) {
                part5++;
            }
            if (MantechConstants.COMPLAINT_STATUS_PENDING == status.getStatusID().intValue()) {
                status1++;
            }
            if (MantechConstants.COMPLAINT_STATUS_RESOLVING == status.getStatusID().intValue()) {
                status2++;
            }
            if (MantechConstants.COMPLAINT_STATUS_HANDING == status.getStatusID().intValue()) {
                status3++;
            }
            if (MantechConstants.COMPLAINT_STATUS_DONE == status.getStatusID().intValue()) {
                status4++;
            }
        }
        dailyArray[0][0] = part1;
        dailyArray[1][0] = part2;
        dailyArray[2][0] = part3;
        dailyArray[3][0] = part4;
        dailyArray[4][0] = part5;
        dailyArray[5][0] = status1;
        dailyArray[6][0] = status2;
        dailyArray[7][0] = status3;
        dailyArray[8][0] = status4;
        return dailyArray;
    }

    public List<Complaint> getDailyWeeklyMonthlyDetail() {
        List<Complaint> allComplaints = complaintFacade.findAll();
        return allComplaints;
    }

    public String previewReport() {
        return "generateReports?faces-redirect=true";
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
        return "newComplaint?faces-redirect=true";
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
        return "viewComplaint?faces-redirect=true";
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
        updateResendAndActualTakenTime(allComplaints);
        if (searchedComplaintID != null && searchedComplaintID.intValue() == 0) {
            searchedComplaintID = null;
        }
        return allComplaints;
    }

    private void updateResendAndActualTakenTime(List<Complaint> allComplaints) {
        if (allComplaints != null && !allComplaints.isEmpty()) {
            for (Complaint cp : allComplaints) {
                //in milliseconds
                if (cp.getStatus().getStatusID() == MantechConstants.COMPLAINT_STATUS_PENDING) {
                    long diffResend = Calendar.getInstance().getTimeInMillis() - cp.getLastModified().getTime();
                    long diffDays = diffResend / (24 * 60 * 60 * 1000);
                    cp.setResend(diffDays >= 2L); //greater than 2 days
                } else if (cp.getStatus().getStatusID() == MantechConstants.COMPLAINT_STATUS_DONE) {
                    updateActualTakenTime(cp, cp.getClosingDate().getTime() - cp.getLodgingDate().getTime());
                }
            }
        }
    }

    private void updateActualTakenTime(Complaint cp, long diff) {
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        cp.setActualTakenDays(diffDays);
        cp.setActualTakenHours(diffHours);
        cp.setActualTakenMinutes(diffMinutes);
        cp.setActualTakenSeconds(diffSeconds);
    }

    public String resendComplaintItem() {
        Complaint cmp = complaintFacade.find(resendComplaintId);
        cmp.setLastModified(Calendar.getInstance().getTime());
        complaintFacade.edit(cmp);

        // save history
        updateHistory(cmp, activityFacade.getResendComplaint());
        return "viewComplaint?faces-redirect=true";
    }

    public String viewComplaintHistory() {
        return "viewComplaintHistory?faces-redirect=true";
    }

    public String viewComplaintAssignment() {
        return "viewComplaintAssignment?faces-redirect=true";
    }

    public String viewComplaintDetail() {
        adminSelectedPriorityId = adminComplaintDetail.getPriority().getPriorityID();
        adminSelectedStatusId = adminComplaintDetail.getStatus().getStatusID();
        UserAccount tech = adminComplaintDetail.getTechnician();
        if (tech != null) {
            adminSelectedTechnician = tech.getAccountID();
        } else {
            adminSelectedTechnician = 0;
        }

        return "viewComplaintDetail?faces-redirect=true";
    }

    public String viewTechComplaintDetail() {
        techInputReasons = techComplaintDetail.getReasons();
        techSelectedCategory = techComplaintDetail.getComplaintCategory().getCategoryID();
        techSelectedStatusId = techComplaintDetail.getStatus().getStatusID();
        return "viewTechComplaintDetail?faces-redirect=true";
    }

    public String updateAdminComplaintDetail() {
        boolean hasChangedTech = checkChangeTech();
        boolean hasChangedStatus = checkChangeStatus();
        boolean hasChangedPriority = checkChangePriority();

        if (!hasChangedTech && !hasChangedStatus && !hasChangedPriority) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "No changes in any field to update!", "Please modify at least one field to update!"));
            return "viewComplaintDetail?faces-redirect=true";
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

        return "viewLastModifiedComplaints?faces-redirect=true";
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
            return "viewTechComplaintDetail?faces-redirect=true";
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
        return "viewComplaintAssignment?faces-redirect=true";
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
        return "viewLastModifiedComplaints?faces-redirect=true";
    }

    public List<Complaint> getAllLatestModifiedComplaints() {
        return complaintFacade.getLastModifiedComplaints();
    }

    public String generateReports() {
        selectedKindOfReport = 1;
        subDmwReport = 11;
        setReportDepartmentWiseId(0);
        setReportTechnicianWiseId(0);
        setReportCategoryWiseId(0);
        return "generateReports?faces-redirect=true";
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

    public List<Complaint> getAllPendingComplaints() {
        List<Complaint> complaints = complaintFacade.getPendingComplaints();

        if (complaints != null && !complaints.isEmpty()) {
            for (Complaint cp : complaints) {
                //in milliseconds
                if (cp.getStatus().getStatusID() == MantechConstants.COMPLAINT_STATUS_PENDING) {
                    updateActualTakenTime(cp, Calendar.getInstance().getTimeInMillis() - cp.getLodgingDate().getTime());
                }
            }
        }
        return complaints;
    }

    public List<Complaint> getAllDepartmentWiseReport() {
        List<Complaint> complaints = complaintFacade.getAllDepartmentWiseReport(getReportDepartmentWiseId());
        updateResendAndActualTakenTime(complaints);
        return complaints;
    }

    public List<Complaint> getAllTechnicianWiseReport() {
        List<Complaint> complaints = complaintFacade.getAllTechnicianWiseReport(getReportTechnicianWiseId());
        updateResendAndActualTakenTime(complaints);
        return complaints;
    }

    public List<Complaint> getAllCategoryWiseReport() {
        List<Complaint> complaints = complaintFacade.getAllCategoryWiseReport(getReportCategoryWiseId());
        updateResendAndActualTakenTime(complaints);
        return complaints;
    }

    /**
     * @return the reportDepartmentWiseId
     */
    public int getReportDepartmentWiseId() {
        return reportDepartmentWiseId;
    }

    /**
     * @param reportDepartmentWiseId the reportDepartmentWiseId to set
     */
    public void setReportDepartmentWiseId(int reportDepartmentWiseId) {
        this.reportDepartmentWiseId = reportDepartmentWiseId;
    }

    /**
     * @return the reportTechnicianWiseId
     */
    public int getReportTechnicianWiseId() {
        return reportTechnicianWiseId;
    }

    /**
     * @param reportTechnicianWiseId the reportTechnicianWiseId to set
     */
    public void setReportTechnicianWiseId(int reportTechnicianWiseId) {
        this.reportTechnicianWiseId = reportTechnicianWiseId;
    }

    /**
     * @return the reportCategoryWiseId
     */
    public int getReportCategoryWiseId() {
        return reportCategoryWiseId;
    }

    /**
     * @param reportCategoryWiseId the reportCategoryWiseId to set
     */
    public void setReportCategoryWiseId(int reportCategoryWiseId) {
        this.reportCategoryWiseId = reportCategoryWiseId;
    }

    /**
     * @return the subDmwReport
     */
    public int getSubDmwReport() {
        return subDmwReport;
    }

    /**
     * @param subDmwReport the subDmwReport to set
     */
    public void setSubDmwReport(int subDmwReport) {
        this.subDmwReport = subDmwReport;
    }
    
    
}
