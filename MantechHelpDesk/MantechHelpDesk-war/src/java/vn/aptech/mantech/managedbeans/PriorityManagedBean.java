/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import vn.aptech.mantech.entity.ComplaintPriority;
import vn.aptech.mantech.sessionbeans.ComplaintPriorityFacadeLocal;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "priority")
@SessionScoped
public class PriorityManagedBean implements Serializable {
    @EJB
    private ComplaintPriorityFacadeLocal complaintPriorityFacade;

    /**
     * Creates a new instance of PriorityManagedBean
     */
    public PriorityManagedBean() {
    }
    
    
    public List<ComplaintPriority> getAllPriorities() {
        return complaintPriorityFacade.findAll();
    }
}
