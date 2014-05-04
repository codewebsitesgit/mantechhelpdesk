/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import vn.aptech.mantech.constants.MantechConstants;

/**
 *
 * @author TruongLQ
 */
public class RolesUtils {
    
    public static final String SUBJECT = "javax.security.auth.subject";
    
    private static final List<String> userPermissions = 
            Arrays.asList("index.xhtml",
                    "changeUserPassword.xhtml",
                    "changeUserProfile.xhtml",
                    "faqs.xhtml",
                    "error.xhtml",
                    "readArticle.xhtml",
                    "userGuide.xhtml",
                    "viewRateResults.xhtml",
                    "newComplaint.xhtml", 
                    "viewComplaint.xhtml");
    private static final List<String> adminPermissions = 
            Arrays.asList("index.xhtml",
                    "changeUserPassword.xhtml",
                    "changeUserProfile.xhtml",
                    "editAccount.xhtml",
                    "faqs.xhtml",
                    "generateReports.xhtml",
                    "userGuide.xhtml",
                    "readArticle.xhtml",
                    "viewRateResults.xhtml",
                    "error.xhtml",
                    "viewLastModifiedComplaints.xhtml", 
                    "viewComplaintDetail.xhtml",
                    "viewComplaintHistory.xhtml",
                    "newAccount.xhtml",
                    "viewAccounts.xhtml");
    private static final List<String> techPermissions = 
            Arrays.asList("index.xhtml",
                    "changeUserPassword.xhtml",
                    "changeUserProfile.xhtml",
                    "faqs.xhtml",
                    "userGuide.xhtml",
                    "newArticle.xhtml", 
                    "error.xhtml",
                    "viewRateResults.xhtml",
                    "maintainArticle.xhtml",
                    "articleDetail.xhtml",
                    "viewComplaintAssignment.xhtml",
                    "viewTechComplaintDetail.xhtml");
    
    public static List<String> getUserPages(final String userRole) {
        if (userRole.equals(MantechConstants.ROLE_ADMIN_DESC)) {
            return adminPermissions;
        }
        if (userRole.equals(MantechConstants.ROLE_USER_DESC)) {
            return userPermissions;
        }
        if (userRole.equals(MantechConstants.ROLE_TECHNICIAN_DESC)) {
            return techPermissions;
        }
        return Collections.emptyList();
    }
}
