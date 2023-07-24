package cool.muyucloud.promotioner.dao;

import cool.muyucloud.promotioner.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author MUYU_Twilighter
 */
public interface CouponDao extends JpaRepository<Coupon, Long> {
}
