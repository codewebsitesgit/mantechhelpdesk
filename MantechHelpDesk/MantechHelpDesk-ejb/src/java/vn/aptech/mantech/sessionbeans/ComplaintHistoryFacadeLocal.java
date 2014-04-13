/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.ComplaintHistory;

/**
 *
 * @author TruongLQ
 */
@Local
public interface ComplaintHistoryFacadeLocal {

    void create(ComplaintHistory complaintHistory);

    void edit(ComplaintHistory complaintHistory);

    void remove(ComplaintHistory complaintHistory);

    ComplaintHistory find(Object id);

    List<ComplaintHistory> findAll();

    List<ComplaintHistory> findRange(int[] range);

    int count();

    int getMaxHistoryID();

    List<ComplaintHistory> getAllSortedComplaintHistories();

    List<ComplaintHistory> getAllNotSelfMadeSortedComplaintHistories(int accountID);

    List<ComplaintHistory> getAllSelfMadeSortedComplaintHistories(int accountID);
    
}
