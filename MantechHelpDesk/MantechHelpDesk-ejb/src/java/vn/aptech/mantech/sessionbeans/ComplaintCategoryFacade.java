/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import vn.aptech.mantech.entity.ComplaintCategory;

/**
 *
 * @author TruongLQ
 */
@Stateless
public class ComplaintCategoryFacade extends AbstractFacade<ComplaintCategory> implements ComplaintCategoryFacadeLocal {
    @PersistenceContext(unitName = "MantechHelpDesk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComplaintCategoryFacade() {
        super(ComplaintCategory.class);
    }
    
}
