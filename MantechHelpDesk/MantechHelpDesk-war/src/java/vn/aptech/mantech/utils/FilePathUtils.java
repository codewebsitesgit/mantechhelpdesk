/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author TruongLQ
 */
public final class FilePathUtils {
    
    public static final String UPLOAD_COMPLAINT_FOLDER = "upload/complaint/"; 
    public static final String UPLOAD_ARTICLE_FOLDER = "upload/article/"; 
    
    public static String getRealPath(final String relativePath) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ServletContext ctx = (ServletContext) fc.getExternalContext().getContext();
        return ctx.getRealPath(relativePath);
    }
    
    public static void saveUploadedImageToDirectory(UploadedFile uploadedFile, final String destFilePath) {
        //create an InputStream from the uploaded file
        InputStream inputStr = null;
        try {
            inputStr = uploadedFile.getInputstream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File destFile = new File(destFilePath);

        //use org.apache.commons.io.FileUtils to copy the File
        try {
            FileUtils.copyInputStreamToFile(inputStr, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean contains(List<UploadedFile> uploadedImages, String imageName) {
        for (UploadedFile f : uploadedImages) {
            if (f.getFileName().equals(imageName)) {
                return true;
            }
        }
        return false;
    }
}
