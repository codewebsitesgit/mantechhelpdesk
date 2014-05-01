/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.aptech.mantech.managedbeans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RateEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.PieChartModel;
import vn.aptech.mantech.entity.Article;
import vn.aptech.mantech.entity.ArticleRate;
import vn.aptech.mantech.entity.ArticleRatePK;
import vn.aptech.mantech.entity.Rate;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.sessionbeans.ArticleFacadeLocal;
import vn.aptech.mantech.sessionbeans.ArticleRateFacadeLocal;
import vn.aptech.mantech.sessionbeans.RateFacadeLocal;
import vn.aptech.mantech.utils.FilePathUtils;

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

    private Integer currentRate = 2;

    private Integer currentArticleId;

    private List<UploadedFile> uploadedImages;

    private UploadedFile currentDelImageName;

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
    }

    public String newArticle() {
        curArticle = new Article();
        curArticle.setArticleID(articleFacade.getMaxArticleID());
        if (uploadedImages != null) {
            uploadedImages.clear();
        }
        return "newArticle?faces-redirect=true";
    }

    public String maintainArticle() {
        return "maintainArticle?faces-redirect=true";
    }

    public String readArticle() {
        return "readArticle?faces-redirect=true";
    }

    public String postArticle() {
        try {
            ut.begin();
            UserAccount user = getSessionUserAccount();
            curArticle.setArticleOwner(user);
            curArticle.setCreationDate(Calendar.getInstance().getTime());
            curArticle.setStatus(true);
            if (uploadedImages != null && !uploadedImages.isEmpty()) {
                curArticle.setImageLocation(FilePathUtils.UPLOAD_ARTICLE_FOLDER + uploadedImages.get(0).getFileName());
            }
            articleFacade.create(curArticle);
            ut.commit();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "The new article was created.", "");
            FacesContext.getCurrentInstance().addMessage("messages", msg);
            if (uploadedImages != null) {
                uploadedImages.clear();
            }

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
        return articleFacade.getAllVisibleArticles();
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
        return "newArticle?faces-redirect=true";
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

            if (!isCreateNew) {
                articleRateFacade.remove(rate);
            }
            ArticleRate newRate = new ArticleRate();
            ArticleRatePK articleRatePK = new ArticleRatePK(articleId, rateStars);
            newRate.setArticleRatePK(articleRatePK);
            newRate.setRateOwner(getSessionUserAccount().getAccountID());
            newRate.setCreationDate(Calendar.getInstance().getTime());
            Rate rating = rateFacade.find(rateStars);
            newRate.setRate(rating);
            Article article = articleFacade.find(articleId);
            newRate.setArticle(article);
            articleRateFacade.create(newRate);
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    public void viewResults() {
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

    /**
     * @return the curPieChart
     */
    public PieChartModel getCurPieChart() {
        PieChartModel curPieChart = new PieChartModel();
        if (currentArticleId == null) {
            return curPieChart;
        }
        // get all the ratings
        List<ArticleRate> poorRatings = articleRateFacade.findPoorRatings(currentArticleId);
        List<ArticleRate> satisfiedRatings = articleRateFacade.findSatisfiedRatings(currentArticleId);
        List<ArticleRate> goodRatings = articleRateFacade.findGoodRatings(currentArticleId);
        List<ArticleRate> veryGoodRatings = articleRateFacade.findVeryGoodRatings(currentArticleId);
        List<ArticleRate> excellentRatings = articleRateFacade.findExcellentRatings(currentArticleId);

        double ps = poorRatings.size();
        double ss = satisfiedRatings.size();
        double gs = goodRatings.size();
        double vgs = veryGoodRatings.size();
        double es = excellentRatings.size();

        double totalSize = ps + ss + gs + vgs + es;
        if (totalSize == 0) {
            return curPieChart;
        }
        double pourPercent = ps / totalSize * 100;
        curPieChart.set("Poor" + "(" + (int) ps + (ps > 1 ? " votes" : " vote") + ")", pourPercent);
        double satisPercent = ss / totalSize * 100;
        curPieChart.set("Satisfied" + "(" + (int) ss + (ss > 1 ? " votes" : " vote") + ")", satisPercent);
        double goodPercent = gs / totalSize * 100;
        curPieChart.set("Good" + "(" + (int) gs + (gs > 1 ? " votes" : " vote") + ")", goodPercent);
        double veryGoodPercent = vgs / totalSize * 100;
        curPieChart.set("Very good" + "(" + (int) vgs + (vgs > 1 ? " votes" : " vote") + ")", veryGoodPercent);
        double excellentPercent = es / totalSize * 100;
        curPieChart.set("Excellent" + "(" + (int) es + (es > 1 ? " votes" : " vote") + ")", excellentPercent);
        return curPieChart;
    }

    public List<Article> getAllSelfActicles() {
        return articleFacade.allSelfArticles(getSessionUserAccount().getAccountID());
    }

    public String updateArticle() {
        if (uploadedImages != null) {
            uploadedImages.clear();

        } else {
            uploadedImages = new ArrayList<UploadedFile>();
        }

        final String imgLocation = curArticle.getImageLocation();
        if (imgLocation != null && !"".equals(imgLocation)) {
            final String[] fileNameFrags = imgLocation.split("/");
            uploadedImages.add(new UploadedFile() {

                @Override
                public String getFileName() {
                    return fileNameFrags[fileNameFrags.length - 1];
                }

                @Override
                public InputStream getInputstream() throws IOException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public long getSize() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public byte[] getContents() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getContentType() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
        }
        return "articleDetail?faces-redirect=true";
    }

    public String doUpdateArticle() {
        try {
            ut.begin();

            Article article = articleFacade.find(curArticle.getArticleID());

            boolean hasImageChanged = hasImagedChange(article);

            if (!hasImageChanged && !hasSubjectChanged(article) && !hasContentsChanged(article)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "No changes in any field to update!", "Please modify at least one field to update!"));
                return "articleDetail";
            }

            if (hasSubjectChanged(article)) {
                article.setArticleSubject(curArticle.getArticleSubject());
            }
            if (hasContentsChanged(article)) {
                article.setArticleContents(curArticle.getArticleContents());
            }

            if (uploadedImages != null && !uploadedImages.isEmpty()) {
                article.setImageLocation(FilePathUtils.UPLOAD_ARTICLE_FOLDER + uploadedImages.get(0).getFileName());
            } else {
                article.setImageLocation(null);
            }

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
        return "maintainArticle?faces-redirect=true";
    }

    private boolean hasImagedChange(Article article) {
        String imgLocation = article.getImageLocation();
        if (imgLocation == null) {
            return uploadedImages != null && !uploadedImages.isEmpty();
        }

        if (!"".equals(imgLocation)) {
            return uploadedImages == null || uploadedImages.isEmpty() || !imgLocation.endsWith(uploadedImages.get(0).getFileName());
        }
        return false;
    }

    private boolean hasSubjectChanged(Article article) {
        return !article.getArticleSubject().equals(curArticle.getArticleSubject().trim());
    }

    private boolean hasContentsChanged(Article article) {
        return !article.getArticleContents().equals(curArticle.getArticleContents().trim());
    }

    /**
     * @return the uploadedImages
     */
    public List<UploadedFile> getUploadedImages() {
        return uploadedImages;
    }

    /**
     * @return the currentDelImageName
     */
    public UploadedFile getCurrentDelImageName() {
        return currentDelImageName;
    }

    public void deleteImage() {
        if (getUploadedImages() != null && !uploadedImages.isEmpty()) {
            if (getUploadedImages().contains(getCurrentDelImageName())) {
                getUploadedImages().remove(getCurrentDelImageName());
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        getCurrentDelImageName().getFileName() + " was deleted successfully.", "");
                FacesContext.getCurrentInstance().addMessage("messages", msg);
                // delete file from folder
                FileUtils.deleteQuietly(new File(FilePathUtils.getRealPath(FilePathUtils.UPLOAD_ARTICLE_FOLDER + getCurrentDelImageName().getFileName())));

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No image named " + getCurrentDelImageName().getFileName() + " found.", "");
                FacesContext.getCurrentInstance().addMessage("messages", msg);
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        if (uploadedImages != null && !uploadedImages.isEmpty()) {
            FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maximum one file is allowed to upload for every article.", "");
            FacesContext.getCurrentInstance().addMessage("messages", errorMsg);
            return;
        }
        final UploadedFile uploadedFile = event.getFile();
        final String fileName = uploadedFile.getFileName();

        // add images to list
        if (getUploadedImages() == null) {
            setUploadedImages(new ArrayList<UploadedFile>());
        }
        // check if exist in database
        if (new File(FilePathUtils.getRealPath(FilePathUtils.UPLOAD_ARTICLE_FOLDER), fileName).exists()) {
            FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "File " + fileName + " already exists! Choose another file or rename it.", "");
            FacesContext.getCurrentInstance().addMessage("messages", errorMsg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    fileName + " was uploaded successfully.", "");
            FacesContext.getCurrentInstance().addMessage("messages", msg);

            getUploadedImages().add(uploadedFile);
            FilePathUtils.saveUploadedImageToDirectory(uploadedFile, FilePathUtils.getRealPath(FilePathUtils.UPLOAD_ARTICLE_FOLDER + fileName));
        }

    }

    /**
     * @param uploadedImages the uploadedImages to set
     */
    public void setUploadedImages(List<UploadedFile> uploadedImages) {
        this.uploadedImages = uploadedImages;
    }

    /**
     * @param currentDelImageName the currentDelImageName to set
     */
    public void setCurrentDelImageName(UploadedFile currentDelImageName) {
        this.currentDelImageName = currentDelImageName;
    }

}
