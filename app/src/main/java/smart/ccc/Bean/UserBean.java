package smart.ccc.Bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class UserBean extends DataSupport {
    private int id;
    private String user;
    private String pwd;
    private String name;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public UserBean() {
    }

    public UserBean(String user, String pwd, String name) {
        this.user = user;
        this.pwd = pwd;
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
