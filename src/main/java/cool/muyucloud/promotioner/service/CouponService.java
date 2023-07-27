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
    void add(String couponName, Double value, Long pid, Date start, Date end);

    boolean use(Long id);

    List<Coupon> get(Long pid, String name, int count);
}
