package cool.muyucloud.promotioner.entity;

import javax.persistence.*;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Entity
@Table(name = "user")
public class User {
    public static final User EMPTY = new User();

    public static final int COMMON = 0;
    public static final int STAFF = 0b00000001;
    public static final int PRIMARY_STAFF = 0b00000010;
    public static final int SECONDARY_STAFF = 0b00000100;
    public static final int DATABASE_ADMIN = 0b10000000;
    public static final int ROOT = 0x0FFFFFFF;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User genHidden() {
        User user = new User();
        user.setUid(this.uid);
        user.setUserName(this.userName);
        user.setAuth(this.auth);
        return user;
    }

    public static boolean isStaff(User user) {
        return (user.auth & STAFF) != 0;
    }

    public static boolean isSecondaryStaff(User user) {
        return (user.auth & SECONDARY_STAFF) != 0;
    }

    public static boolean isPrimaryStaff(User user) {
        return (user.auth & PRIMARY_STAFF) != 0;
    }

    public static boolean isDataBaseAdmin(User user) {
        return (user.auth & DATABASE_ADMIN) != 0;
    }

    public static boolean isRoot(User user) {
        return (user.auth & ROOT) != 0;
    }
}
