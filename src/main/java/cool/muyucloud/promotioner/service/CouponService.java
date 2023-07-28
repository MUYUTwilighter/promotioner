package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.entity.Coupon;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Service
public interface CouponService {
    /**
     * Add coupons for a promotion
     *
     * @param couponName    name of the coupons
     * @param value         value of the coupons
     * @param pid           ID of promotion where the coupons belong to
     * @param start         time when the coupons become available
     * @param end           time when the coupons become unavailable
     *
     * @return true if successfully added, false if exceptions
     * */
    boolean add(String couponName, Double value, Long pid, Date start, Date end);

    boolean use(Long id);

    /**
     * Get specific count of coupons from specific promotion with target name
     *
     * @param pid   promotion ID
     * @param name  name of coupons
     * @param count count of coupons
     *
     * @return a list of coupons, return empty list if exception (pid not found, name invalid, count not satisfied, etc.)
     * */
    List<Coupon> get(Long pid, String name, int count);
}
