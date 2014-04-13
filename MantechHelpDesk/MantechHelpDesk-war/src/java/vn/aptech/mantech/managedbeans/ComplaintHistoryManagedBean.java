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
import vn.aptech.mantech.entity.ComplaintHistory;
import vn.aptech.mantech.sessionbeans.ComplaintHistoryFacadeLocal;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "complaintHistory")
@SessionScoped
public class ComplaintHistoryManagedBean implements Serializable{
    @EJB
    private ComplaintHistoryFacadeLocal complaintHistoryFacade;

    
    /**
     * Creates a new instance of ComplaintHistoryManagedBean
     */
    public ComplaintHistoryManagedBean() {
    }
    
    public List<ComplaintHistory> getAllHistory() {
        return complaintHistoryFacade.getAllSortedComplaintHistories();
    }
}
