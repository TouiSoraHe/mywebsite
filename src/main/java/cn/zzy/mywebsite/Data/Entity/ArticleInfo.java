package cn.zzy.mywebsite.Data.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ArticleInfo {

    private int id;

    private String title;

    private Date time;

    private int articleID;

    private String tag;//标签,格式为:生活|科技

    public ArticleInfo() {
    }

    public ArticleInfo(String title, Date time, int articleID, String tag) {
        this.title = title;
        this.time = time;
        this.articleID = articleID;
        this.tag = tag;
    }

    public ArticleInfo(Article article) {
        this.title = article.getTitle();
        this.time = article.getTime();
        this.articleID = article.getId();
        this.tag = article.getTag();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public String GetFormatTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getTime());
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", articleID=" + articleID +
                ", tag='" + tag + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleInfo)) return false;
        ArticleInfo that = (ArticleInfo) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
