/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.ComplaintCategory;

/**
 *
 * @author TruongLQ
 */
@Local
public interface ComplaintCategoryFacadeLocal {

    void create(ComplaintCategory complaintCategory);

    void edit(ComplaintCategory complaintCategory);

    void remove(ComplaintCategory complaintCategory);

    ComplaintCategory find(Object id);

    List<ComplaintCategory> findAll();

    List<ComplaintCategory> findRange(int[] range);

    int count();
    
}
