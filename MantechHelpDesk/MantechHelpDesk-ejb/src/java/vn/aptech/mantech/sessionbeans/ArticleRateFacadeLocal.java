/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.ArticleRate;

/**
 *
 * @author TruongLQ
 */
@Local
public interface ArticleRateFacadeLocal {

    void create(ArticleRate articleRate);

    void edit(ArticleRate articleRate);

    void remove(ArticleRate articleRate);

    ArticleRate find(Object id);

    List<ArticleRate> findAll();

    List<ArticleRate> findRange(int[] range);

    int count();
    
}
