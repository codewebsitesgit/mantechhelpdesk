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
import vn.aptech.mantech.entity.ComplaintStatus;
import vn.aptech.mantech.sessionbeans.ComplaintStatusFacadeLocal;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "status")
@SessionScoped
public class StatusManagedBean implements Serializable{
    @EJB
    private ComplaintStatusFacadeLocal complaintStatusFacade;

    /**
     * Creates a new instance of StatusManagedBean
     */
    public StatusManagedBean() {
    }
    
    public List<ComplaintStatus> getAllStatus() {
        return complaintStatusFacade.findAll();
    }
    
    public List<ComplaintStatus> getAllStatusExceptPendings() {
        return complaintStatusFacade.getAllStatusExceptPendings();
    }
    
}
