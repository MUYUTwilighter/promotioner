package cool.muyucloud.promotioner.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author MUYU_Twilighter
 */
@Entity
@Table(name = "user")
public class User {
    public static final int COMMON = 0;
    public static final int STAFF = 1;
    public static final int PRIMARY_STAFF = 1 << 1;
    public static final int SECONDARY_STAFF = 1 << 2;
    public static final int DATABASE_ADMIN = 1 << 7;

    @Id
    @Column
    private String id;
    @Column
    private String pwd;
    @Column
    private Integer auth;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public boolean isStaff() {
        return (this.auth & STAFF) != 0;
    }

    public boolean isSecondaryStaff() {
        return (this.auth & SECONDARY_STAFF) != 0;
    }

    public boolean isPrimaryStaff() {
        return (this.auth & PRIMARY_STAFF) != 0;
    }

    public boolean isAdmin() {
        return (this.auth & DATABASE_ADMIN) != 0;
    }
}
