/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import vn.aptech.mantech.constants.MantechConstants;
import vn.aptech.mantech.entity.Activity;

/**
 *
 * @author TruongLQ
 */
@Stateless
public class ActivityFacade extends AbstractFacade<Activity> implements ActivityFacadeLocal {
    @PersistenceContext(unitName = "MantechHelpDesk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActivityFacade() {
        super(Activity.class);
    }

    @Override
    public Activity getResendComplaint() {
        return em.find(Activity.class, MantechConstants.ACTION_RESEND_COMPLAINT);
    }

    @Override
    public Activity getChangeStatus() {
        return em.find(Activity.class, MantechConstants.ACTION_CHANGE_STATUS_COMPLAINT);
    }

    @Override
    public Activity getNewComplaint() {
        return em.find(Activity.class, MantechConstants.ACTION_CREATE_COMPLAINT);
    }

    @Override
    public Activity getUpdateRootCause() {
        return em.find(Activity.class, MantechConstants.ACTION_UPDATE_ROOT_CAUSE_COMPLAINT);
    }

    @Override
    public Activity getChangePriority() {
        return em.find(Activity.class, MantechConstants.ACTION_CHANGE_PRIORITY_COMPLAINT);
    }
    
}
