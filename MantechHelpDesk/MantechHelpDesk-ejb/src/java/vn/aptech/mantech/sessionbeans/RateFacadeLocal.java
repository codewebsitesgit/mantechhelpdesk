/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.sessionbeans;

import java.util.List;
import javax.ejb.Local;
import vn.aptech.mantech.entity.Rate;

/**
 *
 * @author TruongLQ
 */
@Local
public interface RateFacadeLocal {

    void create(Rate rate);

    void edit(Rate rate);

    void remove(Rate rate);

    Rate find(Object id);

    List<Rate> findAll();

    List<Rate> findRange(int[] range);

    int count();
    
}
