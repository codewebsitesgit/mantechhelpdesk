/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import vn.aptech.mantech.entity.Faqs;
import vn.aptech.mantech.sessionbeans.FaqsFacadeLocal;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "faqs")
@SessionScoped
public class FaqsManagedBean implements Serializable {
    @EJB
    private FaqsFacadeLocal faqsFacade;

    private String searchQuestionKeyword;

    public String getSearchQuestionKeyword() {
        return searchQuestionKeyword;
    }

    public void setSearchQuestionKeyword(String searchQuestionKeyword) {
        this.searchQuestionKeyword = searchQuestionKeyword;
    }
    
    
    /**
     * Creates a new instance of FaqsManagedBean
     */
    public FaqsManagedBean() {
    }
    
    public List<Faqs> getAllFaqs() {
        return faqsFacade.searchFaqs(searchQuestionKeyword);
    }
    
    public String searchFaqs() {
        return "faqs";
    }
}
