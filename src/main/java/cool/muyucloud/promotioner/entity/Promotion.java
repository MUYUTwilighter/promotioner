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
    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private Integer category;
    @Column
    private String business;
    @ManyToOne
    @JoinColumn(name = "id")
    private User creator;
    @Column
    private Date start;
    @Column
    private Date end;
    @Column
    private String primaryApprover;
    @Column
    private String secondaryApprover;
    @OneToMany(mappedBy = "promotion")
    private List<Coupon> couponList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCreator() {
        return creator.getId();
    }

    public void setCreator(String creator) {
        this.creator.setId(creator);
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getPrimaryApprover() {
        return primaryApprover;
    }

    public void setPrimaryApprover(String primaryApprover) {
        this.primaryApprover = primaryApprover;
    }

    public String getSecondaryApprover() {
        return secondaryApprover;
    }

    public void setSecondaryApprover(String secondaryApprover) {
        this.secondaryApprover = secondaryApprover;
    }
}
