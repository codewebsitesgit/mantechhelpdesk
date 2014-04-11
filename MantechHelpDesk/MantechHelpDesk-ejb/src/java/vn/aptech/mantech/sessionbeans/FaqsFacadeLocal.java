/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.Faqs;

/**
 *
 * @author TruongLQ
 */
@Local
public interface FaqsFacadeLocal {

    void create(Faqs faqs);

    void edit(Faqs faqs);

    void remove(Faqs faqs);

    Faqs find(Object id);

    List<Faqs> findAll();

    List<Faqs> findRange(int[] range);

    int count();
    
}
