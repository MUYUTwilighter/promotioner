package cool.muyucloud.promotioner.service;

import java.sql.Date;

/**
 * @author MUYU_Twilighter
 */
public interface CouponService {
    void add(String name, Double value, Integer promotionId, Date start, Date end);

    boolean use(Long id);
}
