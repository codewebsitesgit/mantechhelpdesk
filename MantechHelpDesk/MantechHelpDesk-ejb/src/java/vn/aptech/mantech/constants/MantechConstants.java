/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.constants;

/**
 *
 * @author TruongLQ
 */
public interface MantechConstants {

    public static final int ACTION_RESEND_COMPLAINT=1;
    public static final int ACTION_CHANGE_STATUS_COMPLAINT=2;
    public static final int ACTION_CREATE_COMPLAINT=3;
    public static final int ACTION_UPDATE_ROOT_CAUSE_COMPLAINT=4;
    public static final int ACTION_CHANGE_PRIORITY_COMPLAINT=5;
    public static final int ACTION_CHANGE_TECHNICIAN=6;
    public static final int ACTION_CHANGE_COMPLAINT_CATEGORY=7;
    
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_USER = 3;
    public static final int ROLE_TECHNICIAN = 2;
    
    public static final int COMPLAINT_STATUS_DONE = 4;
    public static final int COMPLAINT_STATUS_PENDING = 1;
    public static final int COMPLAINT_STATUS_HANDING = 3;
    public static final int COMPLAINT_STATUS_RESOLVING = 2;
    
    public static final int COMPLAINT_PRIORITY_NORMAL = 2;
    public static final int COMPLAINT_PRIORITY_HIGH = 1;
    public static final int COMPLAINT_PRIORITY_LOW = 3;
    
    public static final int DEPARTMENT_EDU_SERVICE = 1;
    public static final int DEPARTMENT_MNG_SERVICE = 2;
    public static final int DEPARTMENT_LRN_SERVICE = 3;
    public static final int DEPARTMENT_INS_SERVICE= 4;
    public static final int DEPARTMENT_HRS_SERVICE = 5;
    
}
