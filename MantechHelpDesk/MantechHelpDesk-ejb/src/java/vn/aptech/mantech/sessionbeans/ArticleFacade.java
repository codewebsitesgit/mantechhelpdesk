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
import vn.aptech.mantech.entity.Article;

/**
 *
 * @author TruongLQ
 */
@Stateless
public class ArticleFacade extends AbstractFacade<Article> implements ArticleFacadeLocal {
    @PersistenceContext(unitName = "MantechHelpDesk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticleFacade() {
        super(Article.class);
    }

    @Override
    public List<Article> getTopFiveArticles(int accountID) {
        Query query = em.createQuery("SELECT a from Article a WHERE a.articleOwner.accountID =:account ORDER BY a.creationDate DESC");
        query.setParameter("account", accountID);
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public int getMaxArticleID() {
        Query query = em.createQuery("SELECT MAX(a.articleID) from Article a");
        Object obj= query.getSingleResult();
        if (obj == null) {
            return 1;
        }
        return Integer.parseInt(obj.toString()) + 1;
    }

    @Override
    public List<Article> getAllVisibleArticles() {
        Query query = em.createQuery("SELECT a from Article a WHERE a.status =:visible ORDER BY a.creationDate DESC");
        query.setParameter("visible", true);
        return query.getResultList();
    }

    @Override
    public List<Article> allSelfArticles(int accountID) {
        Query query = em.createQuery("SELECT a from Article a WHERE a.articleOwner.accountID =:accID ORDER BY a.creationDate DESC");
        query.setParameter("accID", accountID);
        return query.getResultList();
    }
    
}
