package cool.muyucloud.promotioner.entity;

import jakarta.persistence.*;

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
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer category;
    @Column
    private String business;
    @Column
    private String creator;
    @Column
    private Date start;
    @Column
    private Date end;
    @Column
    private Integer primaryApprover;
    @Column
    private Integer secondaryApprover;
    @OneToMany(mappedBy = "promotion")
    private List<Coupon> couponList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public Integer getPrimaryApprover() {
        return primaryApprover;
    }

    public void setPrimaryApprover(Integer primaryApprover) {
        this.primaryApprover = primaryApprover;
    }

    public Integer getSecondaryApprover() {
        return secondaryApprover;
    }

    public void setSecondaryApprover(Integer secondaryApprover) {
        this.secondaryApprover = secondaryApprover;
    }
}
