package cool.muyucloud.promotioner.entity;

import javax.persistence.*;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    @Column
    private String userName;
    @Column
    private String pwd;
    @Column
    private Integer auth;
    @OneToMany(mappedBy = "creator")
    private List<Promotion> promotions;
    @OneToMany(mappedBy = "primaryApprover")
    private List<Promotion> promotionsPrimary;
    @OneToMany(mappedBy = "secondaryApprover")
    private List<Promotion> promotionsSecondary;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long id) {
        this.uid = id;
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

    public boolean isDataBaseAdmin() {
        return (this.auth & DATABASE_ADMIN) != 0;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
