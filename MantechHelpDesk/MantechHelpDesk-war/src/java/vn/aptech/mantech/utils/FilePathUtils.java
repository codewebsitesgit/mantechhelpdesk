/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.utils;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author TruongLQ
 */
public final class FilePathUtils {
    
    public static final String UPLOAD_COMPLAINT_FOLDER = "upload/complaint/"; 
    
    public static String getRealPath(final String relativePath) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ServletContext ctx = (ServletContext) fc.getExternalContext().getContext();
        return ctx.getRealPath(relativePath);
    }
}
