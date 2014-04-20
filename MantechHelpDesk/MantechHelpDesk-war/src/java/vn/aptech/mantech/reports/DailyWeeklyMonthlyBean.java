/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vn.aptech.mantech.reports;

/**
 *
 * @author TruongLQ
 */
public class DailyWeeklyMonthlyBean {
    
    private String title;
    private int totalDaily;
    private int totalWeekly;
    private int totalMonthly;

    public DailyWeeklyMonthlyBean(String title, int totalDaily, int totalWeekly, int totalMonthly) {
        this.title = title;
        this.totalDaily = totalDaily;
        this.totalWeekly = totalWeekly;
        this.totalMonthly = totalMonthly;
    }

    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the totalDaily
     */
    public int getTotalDaily() {
        return totalDaily;
    }

    /**
     * @param totalDaily the totalDaily to set
     */
    public void setTotalDaily(int totalDaily) {
        this.totalDaily = totalDaily;
    }

    /**
     * @return the totalWeekly
     */
    public int getTotalWeekly() {
        return totalWeekly;
    }

    /**
     * @param totalWeekly the totalWeekly to set
     */
    public void setTotalWeekly(int totalWeekly) {
        this.totalWeekly = totalWeekly;
    }

    /**
     * @return the totalMonthly
     */
    public int getTotalMonthly() {
        return totalMonthly;
    }

    /**
     * @param totalMonthly the totalMonthly to set
     */
    public void setTotalMonthly(int totalMonthly) {
        this.totalMonthly = totalMonthly;
    }

    
}
