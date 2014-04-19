/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.reports;

/**
 *
 * @author TruongLQ
 */
public class ReportKind {
    
    public static final int DAILY_WEEKLY_MONTHY_REPORT = 1; //Daily, Weekly, and Monthly reports
    public static final int DAILY_WEEKLY_MONTHY_SUMMARY_REPORT = 11;
    public static final int DAILY_WEEKLY_MONTHY_DETAIL_REPORT = 12;
    public static final int PENDING_COMPLAINT_REPORT = 2;
    public static final int DEPARTMENT_WISE_REPORT = 3;
    public static final int TECHNICIAN_WISE_REPORT = 4;
    public static final int COMPLAINT_CATEGORY_WISE_REPORT = 5;
    
    public static final String DAILY_WEEKLY_MONTHY_REPORT_DESC = "Daily, Weekly, and Monthly reports";
    public static final String DAILY_WEEKLY_MONTHY_SUMMARY_REPORT_DESC = "Summary report";
    public static final String DAILY_WEEKLY_MONTHY_DETAIL_REPORT_DESC = "Detailed report";
    public static final String PENDING_COMPLAINT_REPORT_DESC = "Status Report of the pending complaints";
    public static final String DEPARTMENT_WISE_REPORT_DESC = "Department wise Report";
    public static final String TECHNICIAN_WISE_REPORT_DESC = "Technician wise report";
    public static final String COMPLAINT_CATEGORY_WISE_REPORT_DESC = "Complaint category wise report";
    
    private int kindId;
    private String name;

    public ReportKind(int kindId, String name) {
        this.kindId = kindId;
        this.name = name;
    }

    /**
     * @return the kindId
     */
    public int getKindId() {
        return kindId;
    }

    /**
     * @param kindId the kindId to set
     */
    public void setKindId(int kindId) {
        this.kindId = kindId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
}
