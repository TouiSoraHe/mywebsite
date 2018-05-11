package cn.zzy.mywebsite.Data.Entity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Article {
    private int id;

    private String title;

    private Date time;

    private String content;

    private int articleInfoID;

    private String tag;//标签,格式为:生活|科技

    public Article() {
    }

    public Article(String title, Date time, String content, int articleInfoID, String tag) {
        this.title = title;
        this.time = time;
        this.content = content;
        this.articleInfoID = articleInfoID;
        this.tag = tag;
    }

    public Article(int id, String title, Date time, String content, int articleInfoID, String tag) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;
        this.articleInfoID = articleInfoID;
        this.tag = tag;
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

    public String GetFormatTime(){
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(getTime());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getArticleInfoID() {
        return articleInfoID;
    }

    public void setArticleInfoID(int articleInfoID) {
        this.articleInfoID = articleInfoID;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return getId() == article.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                ", articleInfoID=" + articleInfoID +
                ", tag='" + tag + '\'' +
                '}';
    }
}
