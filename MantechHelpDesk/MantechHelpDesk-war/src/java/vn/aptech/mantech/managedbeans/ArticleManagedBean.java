/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.managedbeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author TruongLQ
 */
@ManagedBean(name = "article")
@SessionScoped
public class ArticleManagedBean implements Serializable{

    /**
     * Creates a new instance of ArticleManagedBean
     */
    public ArticleManagedBean() {
    }
    
    public String newArticle() {
        return "newArticle";
    }
    
    public String maintainArticle() {
        return "maintainArticle";
    }
    
    public String readArticle() {
        return "readArticle";
    }
}
