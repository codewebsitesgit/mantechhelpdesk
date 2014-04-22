/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import vn.aptech.mantech.entity.Article;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.sessionbeans.ArticleFacadeLocal;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "article")
@SessionScoped
public class ArticleManagedBean implements Serializable {

    @EJB
    private ArticleFacadeLocal articleFacade;

    private Article curArticle;

    @Resource
    private UserTransaction ut;

    /**
     * Creates a new instance of ArticleManagedBean
     */
    public ArticleManagedBean() {
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
}
