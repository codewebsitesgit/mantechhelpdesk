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
import vn.aptech.mantech.constants.MantechConstants;
import vn.aptech.mantech.entity.ArticleRate;

/**
 *
 * @author TruongLQ
 */
@Stateless
public class ArticleRateFacade extends AbstractFacade<ArticleRate> implements ArticleRateFacadeLocal {

    @PersistenceContext(unitName = "MantechHelpDesk-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticleRateFacade() {
        super(ArticleRate.class);
    }

    @Override
    public ArticleRate getArticleRateFromUser(int userId, int articleID) {
        Query query = em.createQuery("SELECT ar from ArticleRate ar WHERE ar.articleRatePK.articleID=:artId AND ar.rateOwner =:account");
        query.setParameter("account", userId);
        query.setParameter("artId", articleID);
        try {
            return (ArticleRate) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<ArticleRate> findPoorRatings(int articleID) {
        Query query = em.createQuery("SELECT ar from ArticleRate ar WHERE ar.article.articleID=:artID AND ar.rate.rateID=:ratID");
        query.setParameter("ratID", MantechConstants.ARTICLE_RATE_POOR);
        query.setParameter("artID", articleID);
        return query.getResultList();
    }

    @Override
    public List<ArticleRate> findSatisfiedRatings(int articleID) {
        Query query = em.createQuery("SELECT ar from ArticleRate ar WHERE ar.article.articleID=:artID AND ar.rate.rateID=:ratID");
        query.setParameter("ratID", MantechConstants.ARTICLE_RATE_SATISFIED);
        query.setParameter("artID", articleID);
        return query.getResultList();
    }

    @Override
    public List<ArticleRate> findGoodRatings(int articleID) {
        Query query = em.createQuery("SELECT ar from ArticleRate ar WHERE ar.article.articleID=:artID AND ar.rate.rateID=:ratID");
        query.setParameter("ratID", MantechConstants.ARTICLE_RATE_GOOD);
        query.setParameter("artID", articleID);
        return query.getResultList();
    }

    @Override
    public List<ArticleRate> findVeryGoodRatings(int articleID) {
        Query query = em.createQuery("SELECT ar from ArticleRate ar WHERE ar.article.articleID=:artID AND ar.rate.rateID=:ratID");
        query.setParameter("ratID", MantechConstants.ARTICLE_RATE_VERY_GOOD);
        query.setParameter("artID", articleID);
        return query.getResultList();
    }

    @Override
    public List<ArticleRate> findExcellentRatings(int articleID) {
        Query query = em.createQuery("SELECT ar from ArticleRate ar WHERE ar.article.articleID=:artID AND ar.rate.rateID=:ratID");
        query.setParameter("ratID", MantechConstants.ARTICLE_RATE_EXCELLENT);
        query.setParameter("artID", articleID);
        return query.getResultList();
    }
}
