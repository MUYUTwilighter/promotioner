package cool.muyucloud.promotioner.entity;

import jakarta.persistence.*;

import java.sql.Date;

/**
 * @author MUYU_Twilighter
 */
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @Column
    private Long id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "promotion")
    private Promotion promotion;
    @Column
    private Double value;
    @Column
    private Date start;
    @Column
    private Date end;
    @Column
    private Boolean used;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
