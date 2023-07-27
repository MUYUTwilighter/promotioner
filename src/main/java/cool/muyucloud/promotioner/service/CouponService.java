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
    void add(String name, Double value, String promotionId, Date start, Date end);

    boolean use(String id);

    List<Coupon> get(String promotionId, String name, int count);
}
