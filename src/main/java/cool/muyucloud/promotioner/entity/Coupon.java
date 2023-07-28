package cool.muyucloud.promotioner.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Entity
@Table(name = "coupon")
public class Coupon {
    public static final Coupon EMPTY = new Coupon();
    public static final List<Coupon> EMPTY_LIST = new ArrayList<>();

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    @Column
    private String couponName;
    @ManyToOne
    @JoinColumn(name = "promotion", referencedColumnName = "pid")
    private Promotion promotion;
    @Column
    private Double value;
    @Column
    private Date start;
    @Column
    private Date end;
    @Column
    private Boolean used;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long id) {
        this.cid = id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
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

    public static class generator {
    }
}
