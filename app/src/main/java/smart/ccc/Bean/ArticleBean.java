package smart.ccc.Bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/16 0016.
 */


public class ArticleBean extends DataSupport implements Serializable{
    private int id;
    private String title;
    private byte[] imagebyte;
    private String content;
    private String author;
    private String classify;
    private boolean isMe;
    private String user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArticleBean() {
    }

    public ArticleBean(String title, byte[] imagebyte, String content, String author, String classify, boolean isMe, String user) {
        this.title = title;
        this.imagebyte = imagebyte;
        this.content = content;
        this.author = author;
        this.classify = classify;
        this.user=user;
        this.isMe = isMe;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getImagebyte() {
        return imagebyte;
    }

    public void setImagebyte(byte[] imagebyte) {
        this.imagebyte = imagebyte;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}
