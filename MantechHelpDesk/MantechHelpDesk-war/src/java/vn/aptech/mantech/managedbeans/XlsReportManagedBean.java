/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.mantech.managedbeans;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vn.aptech.mantech.constants.MantechConstants;
import vn.aptech.mantech.entity.Complaint;
import vn.aptech.mantech.entity.ComplaintCategory;
import vn.aptech.mantech.entity.Department;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.reports.DailyWeeklyMonthlyBean;
import vn.aptech.mantech.sessionbeans.ComplaintCategoryFacadeLocal;
import vn.aptech.mantech.sessionbeans.DepartmentFacadeLocal;
import vn.aptech.mantech.sessionbeans.UserAccountFacadeLocal;
import vn.aptech.mantech.utils.ReportUtils;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "xlsReport")
@SessionScoped
public class XlsReportManagedBean implements Serializable {

    @EJB
    private UserAccountFacadeLocal userAccountFacade;
    @EJB
    private ComplaintCategoryFacadeLocal complaintCategoryFacade;
    @EJB
    private DepartmentFacadeLocal departmentFacade;

    private static final SimpleDateFormat SF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static final String DWM_SUMMARY_FILE_NAME = "DailyWeeklyMonthlySummary";
    private static final String DWM_DETAIL_FILE_NAME = "DailyWeeklyMonthlyDetail";
    private static final String ALL_PENDING_COMPLAINTS_FILE_NAME = "PendingComplaints";
    private static final String DEPARTMENT_WISE_FILE_NAME = "DepartmentWiseReport";
    private static final String TECHNICIAN_WISE_FILE_NAME = "TechnicianWiseReport";
    private static final String CATEGORY_WISE_FILE_NAME = "CategoryWiseReport";
    private static final String REPORT_FOLDER = "report";

    private Integer departmentId;
    private Integer technicianId;
    private Integer categoryId;

    private String dwmReportDetailSubKind;

    /**
     * Creates a new instance of XlsReportManagedBean
     */
    public XlsReportManagedBean() {
    }

    public void exportDwmReportSummaryToExcel(List<DailyWeeklyMonthlyBean> contents) throws IOException {
        if (contents.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "No data to export!", "The data table is empty!"));
            return;
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        FileInputStream file = ReportUtils.getTemplate(fc, REPORT_FOLDER, DWM_SUMMARY_FILE_NAME, DWM_SUMMARY_FILE_NAME);
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = buildDwmReportSummaryWorkbook(file, contents);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        file.close();
        fc.responseComplete();
    }

    public void exportDwmComplaintsToExcel(List<Complaint> contents) throws IOException {
        if (contents.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "No data to export!", "The data table is empty!"));
            return;
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        FileInputStream file = ReportUtils.getTemplate(fc, REPORT_FOLDER, dwmReportDetailSubKind.replace(" ", "_"),DWM_DETAIL_FILE_NAME);
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = buildDwmComplaintsWorkbook(file, contents);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        file.close();
        fc.responseComplete();
    }

    public void exportAllPendingComplaintsToExcel(List<Complaint> contents) throws IOException {
        if (contents.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "No data to export!", "The data table is empty!"));
            return;
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        FileInputStream file = ReportUtils.getTemplate(fc, REPORT_FOLDER, ALL_PENDING_COMPLAINTS_FILE_NAME,ALL_PENDING_COMPLAINTS_FILE_NAME);
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = buildPendingComplaintsWorkbook(file, contents);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        file.close();
        fc.responseComplete();
    }

    private XSSFWorkbook buildPendingComplaintsWorkbook(FileInputStream file, List<Complaint> contents) throws IOException {
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        //Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        int size = contents.size();
        XSSFRow row0Template = sheet.getRow(4);
        XSSFRow row1Template = sheet.getRow(5);
        for (int i = 4; i < 4 + size; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            copyRowStyle(row, 6, i, row0Template, row1Template);
            
            Complaint d = contents.get(i - 4);
            row.getCell(0).setCellValue(d.getComplaintID());
            row.getCell(1).setCellValue(d.getComplaintSubject());
            row.getCell(2).setCellValue(d.getComplaintOwner().getDepartmentID().getDepartmentName());
            row.getCell(3).setCellValue(d.getComplaintOwner().getName());
            row.getCell(4).setCellValue(d.getActualTakenDays() + "d:" + d.getActualTakenHours()
                    + "h:" + d.getActualTakenMinutes() + "m:" + d.getActualTakenSeconds() + "s");
            row.getCell(5).setCellValue(d.getPriority().getPriorityName());
        }
        return workbook;
    }

    public void exportAllDepartmentWiseReportToExcel(List<Complaint> contents) throws IOException {
        if (contents.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "No data to export!", "The data table is empty!"));
            return;
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        Department dpt = departmentFacade.find(departmentId.intValue());
        FileInputStream file = ReportUtils.getTemplate(fc, REPORT_FOLDER, 
                DEPARTMENT_WISE_FILE_NAME + "_"+dpt.getDepartmentName().replace(" ", "_"), DEPARTMENT_WISE_FILE_NAME);
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = buildDepartmentWiseReportWorkbook(file, contents, dpt);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        file.close();
        fc.responseComplete();
    }

    private XSSFWorkbook buildDepartmentWiseReportWorkbook(FileInputStream file, 
            List<Complaint> contents, Department dpt) throws IOException {
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        //Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        int size = contents.size();
        XSSFRow row0Template = sheet.getRow(4);
        XSSFRow row1Template = sheet.getRow(5);
        XSSFRow departmentRow = sheet.getRow(1);
        departmentRow.getCell(1).setCellValue(dpt.getDepartmentName());
        for (int i = 4; i < 4 + size; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            copyRowStyle(row, 6, i, row0Template, row1Template);
            
            Complaint d = contents.get(i - 4);
            row.getCell(0).setCellValue(d.getComplaintCategory().getCategoryName());
            row.getCell(1).setCellValue(d.getComplaintOwner().getAccountID() + "/" + d.getComplaintOwner().getName());
            row.getCell(2).setCellValue(SF.format(d.getLodgingDate()));
            row.getCell(3).setCellValue((d.getClosingDate() == null) ? "" : SF.format(d.getClosingDate()));
            row.getCell(4).setCellValue((d.getTechnician() == null) ? "" : d.getTechnician().getName());
            if (d.getStatus().getStatusID().intValue() == MantechConstants.COMPLAINT_STATUS_DONE) {
                row.getCell(5).setCellValue(d.getActualTakenDays() + "d:" + d.getActualTakenHours()
                        + "h:" + d.getActualTakenMinutes() + "m:" + d.getActualTakenSeconds() + "s");
            } else {
                row.getCell(5).setCellValue("");
            }
        }
        return workbook;
    }

    public void exportAllTechnicianWiseReportToExcel(List<Complaint> contents) throws IOException {
        if (contents.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "No data to export!", "The data table is empty!"));
            return;
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        UserAccount acc = userAccountFacade.find(technicianId.intValue());
        FileInputStream file = ReportUtils.getTemplate(fc, REPORT_FOLDER, 
                TECHNICIAN_WISE_FILE_NAME +"_"+acc.getName().replace(" ", "_"), TECHNICIAN_WISE_FILE_NAME);
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = buildTechnicianWiseReportWorkbook(file, contents, acc);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        file.close();
        fc.responseComplete();
    }

    private XSSFWorkbook buildTechnicianWiseReportWorkbook(FileInputStream file, 
            List<Complaint> contents, UserAccount acc) throws IOException {
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        //Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        int size = contents.size();
        XSSFRow row0Template = sheet.getRow(4);
        XSSFRow row1Template = sheet.getRow(5);
        XSSFRow technicianRow = sheet.getRow(1);
        technicianRow.getCell(1).setCellValue(acc.getName());
        for (int i = 4; i < 4 + size; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            copyRowStyle(row, 6, i, row0Template, row1Template);
            Complaint d = contents.get(i - 4);
            row.getCell(0).setCellValue(d.getComplaintCategory().getCategoryName());
            row.getCell(1).setCellValue(d.getComplaintOwner().getAccountID() + "/" + d.getComplaintOwner().getName());
            row.getCell(2).setCellValue(d.getComplaintOwner().getDepartmentID().getDepartmentName());
            row.getCell(3).setCellValue(SF.format(d.getLodgingDate()));
            row.getCell(4).setCellValue((d.getClosingDate() == null) ? "" : SF.format(d.getClosingDate()));
            if (d.getStatus().getStatusID().intValue() == MantechConstants.COMPLAINT_STATUS_DONE) {
                row.getCell(5).setCellValue(d.getActualTakenDays() + "d:" + d.getActualTakenHours()
                        + "h:" + d.getActualTakenMinutes() + "m:" + d.getActualTakenSeconds() + "s");
            } else {
                row.getCell(5).setCellValue("");
            }
        }
        return workbook;
    }

    public void exportAllCategoryWiseReportToExcel(List<Complaint> contents) throws IOException {
        if (contents.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_WARN, "No data to export!", "The data table is empty!"));
            return;
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        
        ComplaintCategory cat = complaintCategoryFacade.find(categoryId.intValue());
        
        FileInputStream file = ReportUtils.getTemplate(fc, REPORT_FOLDER, 
                CATEGORY_WISE_FILE_NAME + "_"+ cat.getCategoryName().replace(" ", "_"), CATEGORY_WISE_FILE_NAME);
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = buildCategoryWiseReportWorkbook(file, contents, cat);

        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        file.close();
        fc.responseComplete();
    }

    private XSSFWorkbook buildCategoryWiseReportWorkbook(FileInputStream file, 
            List<Complaint> contents, ComplaintCategory cat) throws IOException {
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        //Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        int size = contents.size();
        XSSFRow row0Template = sheet.getRow(4);
        XSSFRow row1Template = sheet.getRow(5);
        XSSFRow cateRow = sheet.getRow(1);
        cateRow.getCell(1).setCellValue(cat.getCategoryName());
        for (int i = 4; i < 4 + size; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            copyRowStyle(row, 6, i, row0Template, row1Template);
            
            Complaint d = contents.get(i - 4);
            row.getCell(0).setCellValue(d.getComplaintOwner().getAccountID() + "/" + d.getComplaintOwner().getName());
            row.getCell(1).setCellValue(d.getComplaintOwner().getDepartmentID().getDepartmentName());
            row.getCell(2).setCellValue(SF.format(d.getLodgingDate()));
            row.getCell(3).setCellValue((d.getClosingDate() == null) ? "" : SF.format(d.getClosingDate()));
            row.getCell(4).setCellValue((d.getTechnician() == null) ? "" : d.getTechnician().getName());
            if (d.getStatus().getStatusID().intValue() == MantechConstants.COMPLAINT_STATUS_DONE) {
                row.getCell(5).setCellValue(d.getActualTakenDays() + "d:" + d.getActualTakenHours()
                        + "h:" + d.getActualTakenMinutes() + "m:" + d.getActualTakenSeconds() + "s");
            } else {
                row.getCell(5).setCellValue("");
            }
        }
        return workbook;
    }

    private XSSFWorkbook buildDwmReportSummaryWorkbook(FileInputStream file,
            List<DailyWeeklyMonthlyBean> contents) throws IOException {
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        //Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (int i = 4; i < 14; i++) {
            XSSFRow row = sheet.getRow(i);
            DailyWeeklyMonthlyBean b = contents.get(i - 4);
            row.getCell(0).setCellValue(b.getTitle());
            row.getCell(1).setCellValue(b.getTotalDaily());
            row.getCell(2).setCellValue(b.getTotalWeekly());
            row.getCell(3).setCellValue(b.getTotalMonthly());
        }
        return workbook;
    }

    private XSSFWorkbook buildDwmComplaintsWorkbook(FileInputStream file,
            List<Complaint> dailyComplaints) throws IOException {
        //Get the workbook instance for XLS file
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        //Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        int size = dailyComplaints.size();

        XSSFRow row0Template = sheet.getRow(4);
        XSSFRow row1Template = sheet.getRow(5);
        
        XSSFRow dwmSubKind = sheet.getRow(1);
        dwmSubKind.getCell(1).setCellValue(dwmReportDetailSubKind);
        for (int i = 4; i < 4 + size; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            copyRowStyle(row, 9, i, row0Template, row1Template);
            
            Complaint d = dailyComplaints.get(i - 4);
            row.getCell(0).setCellValue(d.getComplaintID());
            row.getCell(1).setCellValue(d.getComplaintCategory().getCategoryName());
            row.getCell(2).setCellValue(SF.format(d.getLodgingDate()));
            row.getCell(3).setCellValue((d.getClosingDate() == null) ? "" : SF.format(d.getClosingDate()));
            row.getCell(4).setCellValue(d.getComplaintOwner().getDepartmentID().getDepartmentName());
            row.getCell(5).setCellValue((d.getTechnician() == null) ? "" : d.getTechnician().getName());
            if (d.getStatus().getStatusID().intValue() == MantechConstants.COMPLAINT_STATUS_DONE) {
                row.getCell(6).setCellValue(d.getActualTakenDays() + "d:" + d.getActualTakenHours()
                        + "h:" + d.getActualTakenMinutes() + "m:" + d.getActualTakenSeconds() + "s");
            } else {
                row.getCell(6).setCellValue("");
            }
            row.getCell(7).setCellValue(d.getComplaintOwner().getAccountID());
            row.getCell(8).setCellValue(d.getComplaintOwner().getName());
        }
        return workbook;
    }

    private void copyRowStyle(XSSFRow row, int cellMaxNum, int rowIdx, XSSFRow row0Template, XSSFRow row1Template) {
        
        for (int i = 0; i < cellMaxNum; i++) {
            XSSFCell cell = row.getCell(i);
            if (cell == null) {
                cell = row.createCell(i);
            }
            cell.setCellStyle(((rowIdx % 2) == 0) ? row0Template.getCell(i).getCellStyle() : row1Template.getCell(i).getCellStyle());
        }
    }

    
    /**
     * @return the departmentId
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the technicianId
     */
    public Integer getTechnicianId() {
        return technicianId;
    }

    /**
     * @param technicianId the technicianId to set
     */
    public void setTechnicianId(Integer technicianId) {
        this.technicianId = technicianId;
    }

    /**
     * @return the categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the dwmReportDetailSubKind
     */
    public String getDwmReportDetailSubKind() {
        return dwmReportDetailSubKind;
    }

    /**
     * @param dwmReportDetailSubKind the dwmReportDetailSubKind to set
     */
    public void setDwmReportDetailSubKind(String dwmReportDetailSubKind) {
        this.dwmReportDetailSubKind = dwmReportDetailSubKind;
    }

}
