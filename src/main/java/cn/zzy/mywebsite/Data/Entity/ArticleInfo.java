package cn.zzy.mywebsite.Data.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ArticleInfo {

    private int id;

    private String title;

    private Date time;

    private int articleID;

    public ArticleInfo() {
    }

    public ArticleInfo(String title, Date time, int articleID) {
        this.title = title;
        this.time = time;
        this.articleID = articleID;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", articleID=" + articleID +
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
