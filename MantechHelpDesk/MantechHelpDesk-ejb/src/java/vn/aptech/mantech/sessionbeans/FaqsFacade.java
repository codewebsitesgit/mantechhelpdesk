/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import vn.aptech.mantech.entity.Faqs;

/**
 *
 * @author TruongLQ
 */
@Stateless
public class FaqsFacade extends AbstractFacade<Faqs> implements FaqsFacadeLocal {
    @PersistenceContext(unitName = "MantechHelpDesk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FaqsFacade() {
        super(Faqs.class);
    }

    @Override
    public List<Faqs> searchFaqs(String searchedKeyword) {
        String sql = "SELECT f from Faqs f";
        if (searchedKeyword != null && !searchedKeyword.isEmpty()) {
            sql = "SELECT f from Faqs f WHERE f.faqSubject like :keywordVal OR f.faqContents LIKE :keywordVal";
        }
        Query query = em.createQuery(sql);
        if (searchedKeyword != null && !searchedKeyword.isEmpty()) {
            query.setParameter("keywordVal", "%" +searchedKeyword+"%");
        }
        
        return query.getResultList();
    }
    
}
