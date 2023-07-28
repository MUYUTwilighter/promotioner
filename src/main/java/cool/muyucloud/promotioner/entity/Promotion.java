package cool.muyucloud.promotioner.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Entity
@Table(name = "promotion")
public class Promotion {
    public static final Promotion EMPTY = new Promotion();

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;
    @Column
    private String promotionName;
    @Column
    private Integer category;
    @Column
    private String business;
    @ManyToOne
    @JoinColumn(name = "creator", referencedColumnName = "uid")
    private User creator;
    @Column
    private Date startDate;
    @Column
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "primary_approver", referencedColumnName = "uid")
    private User primaryApprover;
    @ManyToOne
    @JoinColumn(name = "secondary_approver", referencedColumnName = "uid")
    private User secondaryApprover;
    @OneToMany(mappedBy = "promotion")
    private List<Coupon> couponList;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long id) {
        this.pid = id;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getPrimaryApprover() {
        return primaryApprover;
    }

    public void setPrimaryApprover(User primaryApprover) {
        this.primaryApprover = primaryApprover;
    }

    public User getSecondaryApprover() {
        return secondaryApprover;
    }

    public void setSecondaryApprover(User secondaryApprover) {
        this.secondaryApprover = secondaryApprover;
    }
}
