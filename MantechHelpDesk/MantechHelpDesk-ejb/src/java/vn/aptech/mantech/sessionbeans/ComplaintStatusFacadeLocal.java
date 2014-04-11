/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.ComplaintStatus;

/**
 *
 * @author TruongLQ
 */
@Local
public interface ComplaintStatusFacadeLocal {

    void create(ComplaintStatus complaintStatus);

    void edit(ComplaintStatus complaintStatus);

    void remove(ComplaintStatus complaintStatus);

    ComplaintStatus find(Object id);

    List<ComplaintStatus> findAll();

    List<ComplaintStatus> findRange(int[] range);

    int count();
    
}
