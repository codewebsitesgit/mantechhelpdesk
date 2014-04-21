/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TruongLQ
 */
public final class ReportUtils {
    
    private static final String EXCEL_EXTENSION = ".xlsx";
    
    public static FileInputStream getTemplate(FacesContext fc, String folder, String fileName, String internalFileName) throws FileNotFoundException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd_HHmmss_");
        String filename = fmt.format(Calendar.getInstance().getTime()) + fileName +EXCEL_EXTENSION;
        String contentType = "application/vnd.ms-excel";
        ServletContext ctx = (ServletContext) fc.getExternalContext().getContext();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        response.setContentType(contentType);
        String realPath = ctx.getRealPath(folder+ "/" + internalFileName +EXCEL_EXTENSION);
        return new FileInputStream(realPath);
    }
}
