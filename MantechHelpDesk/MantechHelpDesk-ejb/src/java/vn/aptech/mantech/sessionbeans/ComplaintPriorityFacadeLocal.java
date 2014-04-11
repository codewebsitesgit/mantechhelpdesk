/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.ComplaintPriority;

/**
 *
 * @author TruongLQ
 */
@Local
public interface ComplaintPriorityFacadeLocal {

    void create(ComplaintPriority complaintPriority);

    void edit(ComplaintPriority complaintPriority);

    void remove(ComplaintPriority complaintPriority);

    ComplaintPriority find(Object id);

    List<ComplaintPriority> findAll();

    List<ComplaintPriority> findRange(int[] range);

    int count();
    
}
