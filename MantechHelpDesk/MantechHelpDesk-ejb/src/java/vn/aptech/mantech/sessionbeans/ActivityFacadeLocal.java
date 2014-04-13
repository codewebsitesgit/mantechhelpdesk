/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.Activity;

/**
 *
 * @author TruongLQ
 */
@Local
public interface ActivityFacadeLocal {

    void create(Activity activity);

    void edit(Activity activity);

    void remove(Activity activity);

    Activity find(Object id);

    List<Activity> findAll();

    List<Activity> findRange(int[] range);

    int count();

    Activity getResendComplaint();

    Activity getChangeStatus();

    Activity getNewComplaint();

    Activity getUpdateRootCause();

    Activity getChangePriority();
    
    Activity getAssignTechnician();

    Activity getChangeCategory();
}
