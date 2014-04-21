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
import vn.aptech.mantech.entity.Department;
import vn.aptech.mantech.sessionbeans.DepartmentFacadeLocal;

/**
 *
 * @author KimHao
 */
@ManagedBean(name = "department")
@SessionScoped
public class DepartmentManagedBean implements Serializable{

    @EJB
    private DepartmentFacadeLocal departmentFacade;

    /**
     * Creates a new instance of DepartmentManagedBean
     */
    public DepartmentManagedBean() {
    }

    public List<Department> getAllDepartment() {
        return departmentFacade.findAll();
    }

}
