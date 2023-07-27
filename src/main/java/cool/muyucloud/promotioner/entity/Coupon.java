package cool.muyucloud.promotioner.entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author MUYU_Twilighter
 */
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @Column
    private String cid;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "pid")
    private Promotion promotion;
    @Column
    private Double value;
    @Column
    private Date start;
    @Column
    private Date end;
    @Column
    private Boolean used;

    public String getCid() {
        return cid;
    }

    public void setCid(String id) {
        this.cid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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

    public Boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
