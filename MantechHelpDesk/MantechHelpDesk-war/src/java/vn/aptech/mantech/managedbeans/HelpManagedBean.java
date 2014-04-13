/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "help")
@SessionScoped
public class HelpManagedBean implements Serializable {

    /**
     * Creates a new instance of HelpManagedBean
     */
    public HelpManagedBean() {
    }
 
    public String viewUserGuide() {
        return "userGuide";
    }
    
    public String viewFAQs() {
        return "faqs";
    }
}
