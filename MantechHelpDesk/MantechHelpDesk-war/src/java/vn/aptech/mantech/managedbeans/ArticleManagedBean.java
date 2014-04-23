/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RateEvent;
import org.primefaces.model.chart.PieChartModel;
import vn.aptech.mantech.entity.Article;
import vn.aptech.mantech.entity.ArticleRate;
import vn.aptech.mantech.entity.ArticleRatePK;
import vn.aptech.mantech.entity.Rate;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.sessionbeans.ArticleFacadeLocal;
import vn.aptech.mantech.sessionbeans.ArticleRateFacadeLocal;
import vn.aptech.mantech.sessionbeans.RateFacadeLocal;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "article")
@SessionScoped
public class ArticleManagedBean implements Serializable {

    @EJB
    private ArticleRateFacadeLocal articleRateFacade;

    @EJB
    private RateFacadeLocal rateFacade;

    @EJB
    private ArticleFacadeLocal articleFacade;

    private Article curArticle;

    private int changedArticleStatusId;

    private final Map<Integer, Integer> ratings;

    private Integer currentRate = 2;

    private Integer currentArticleId;

    public Integer getCurrentArticleId() {
        return currentArticleId;
    }

    public void setCurrentArticleId(Integer currentArticleId) {
        this.currentArticleId = currentArticleId;
    }
    
    @Resource
    private UserTransaction ut;

    /**
     * Creates a new instance of ArticleManagedBean
     */
    public ArticleManagedBean() {
        ratings = new HashMap<Integer, Integer>();
    }

    public String newArticle() {
        curArticle = new Article();
        curArticle.setArticleID(articleFacade.getMaxArticleID());
        return "newArticle";
    }

    public String maintainArticle() {
        return "maintainArticle";
    }

    public String readArticle() {
        return "readArticle";
    }

    public String postArticle() {
        try {
            ut.begin();
            UserAccount user = getSessionUserAccount();
            curArticle.setArticleOwner(user);
            curArticle.setCreationDate(Calendar.getInstance().getTime());
            curArticle.setStatus(true);
            articleFacade.create(curArticle);
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return newArticle();
    }

    public List<Article> getTopFiveArticles() {
        return articleFacade.getTopFiveArticles(getSessionUserAccount().getAccountID());
    }

    public List<Article> getAllVisibleArticles() {
        try {
            ut.begin();
            List<Article> articles = articleFacade.getAllVisibleArticles();
            int accId = getSessionUserAccount().getAccountID();
            for (Article a : articles) {
                for (ArticleRate r : a.getArticleRateCollection()) {
                    if (r.getRateOwner() == accId) {
                        a.setCurrentRate(r);
                        break;
                    }
                }

            }
            ut.commit();
            return articles;
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private static UserAccount getSessionUserAccount() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
        return (UserAccount) session.getAttribute("userSession");
    }

    /**
     * @return the curArticle
     */
    public Article getCurArticle() {
        return curArticle;
    }

    /**
     * @param curArticle the curArticle to set
     */
    public void setCurArticle(Article curArticle) {
        this.curArticle = curArticle;
    }

    public String changeStatus() {
        try {
            ut.begin();
            Article article = articleFacade.find(changedArticleStatusId);
            article.setStatus(!article.getStatus());
            articleFacade.edit(article);
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return "newArticle";
    }

    /**
     * @return the changedArticleStatusId
     */
    public int getChangedArticleStatusId() {
        return changedArticleStatusId;
    }

    /**
     * @param changedArticleStatusId the changedArticleStatusId to set
     */
    public void setChangedArticleStatusId(int changedArticleStatusId) {
        this.changedArticleStatusId = changedArticleStatusId;
    }

    public void handleRate(RateEvent rateEvent) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int articleId = Integer.parseInt(params.get("articleId"));
        int rateStars = Integer.parseInt(rateEvent.getRating().toString());
        // save to database
        try {
            ut.begin();

            ArticleRate rate = articleRateFacade.getArticleRateFromUser(
                    getSessionUserAccount().getAccountID(), articleId);
            final boolean isCreateNew = rate == null;
            if (rate == null) {
                rate = new ArticleRate();
                ArticleRatePK articleRatePK = new ArticleRatePK(articleId, rateStars);
                rate.setArticleRatePK(articleRatePK);
                Article article = articleFacade.find(articleId);
                rate.setArticle(article);
                rate.setRateOwner(getSessionUserAccount().getAccountID());
            }
            Rate rating = rateFacade.find(rateStars);
            rate.setRate(rating);
            rate.setCreationDate(Calendar.getInstance().getTime());
            if (isCreateNew) {
                articleRateFacade.create(rate);
            } else {
                articleRateFacade.edit(rate);
            }

            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        ratings.put(articleId, rateStars);
    }

    public void viewResults() {
    }

    public PieChartModel getPieRatingResultModel() {
        PieChartModel pieModel = new PieChartModel();

//        if(currentArticleId == null) {
//            return pieModel;
//        }
//        // get all the ratings
//        List<ArticleRate> poorRatings = articleRateFacade.findPoorRatings(currentArticleId.intValue());
//        List<ArticleRate> satisfiedRatings = articleRateFacade.findSatisfiedRatings(currentArticleId.intValue());
//        List<ArticleRate> goodRatings = articleRateFacade.findGoodRatings(currentArticleId.intValue());
//        List<ArticleRate> veryGoodRatings = articleRateFacade.findVeryGoodRatings(currentArticleId.intValue());
//        List<ArticleRate> excellentRatings = articleRateFacade.findExcellentRatings(currentArticleId.intValue());
//
//        double ps = poorRatings.size();
//        double ss = satisfiedRatings.size();
//        double gs = goodRatings.size();
//        double vgs = veryGoodRatings.size();
//        double es = excellentRatings.size();
//
//        double totalSize = ps + ss + gs + vgs + es;
//        if (totalSize == 0) {
//            return pieModel;
//        }
//        pieModel.set("Poor", ps / totalSize * 1000);
//        pieModel.set("Satisfied", ss / totalSize * 1000);
//        pieModel.set("Good", gs / totalSize * 10000);
//        pieModel.set("Very good", vgs / totalSize * 1000);
//        pieModel.set("Excellent", es / totalSize * 1000);
        
        pieModel.set("Poor", 540);
        pieModel.set("Satisfied", 325);
        pieModel.set("Good", 702);
        pieModel.set("Very good", 421);
        pieModel.set("Excellent", 274);
        
        return pieModel;
    }

    /**
     * @return the currentRate
     */
    public Integer getCurrentRate() {
        return currentRate;
    }

    /**
     * @param currentRate the currentRate to set
     */
    public void setCurrentRate(Integer currentRate) {
        this.currentRate = currentRate;
    }

    public String getCurrentRateDesc() {
        if (currentRate != null) {
            Rate rate = rateFacade.find(currentRate.intValue());
            if (rate != null) {
                return "<font color='red'>" + rate.getRateDesc() + "</font>";
            }
        }

        return "";
    }
}
