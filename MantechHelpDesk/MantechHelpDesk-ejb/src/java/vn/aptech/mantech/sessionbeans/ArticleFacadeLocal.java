/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.Article;

/**
 *
 * @author TruongLQ
 */
@Local
public interface ArticleFacadeLocal {

    void create(Article article);

    void edit(Article article);

    void remove(Article article);

    Article find(Object id);

    List<Article> findAll();

    List<Article> findRange(int[] range);

    int count();

    List<Article> getTopFiveArticles(int accountID);

    int getMaxArticleID();

    List<Article> getAllVisibleArticles();

    List<Article> allSelfArticles(int accountID);
    
}
