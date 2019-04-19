package smart.ccc.Bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class CommentBean extends DataSupport {
    private int id;
    private long article_i;
    private String user;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public long getArticle_i() {
        return article_i;
    }

    public void setArticle_i(long article_i) {
        this.article_i = article_i;
    }



    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
