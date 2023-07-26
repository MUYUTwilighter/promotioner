package cool.muyucloud.promotioner.dao;

import cool.muyucloud.promotioner.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author MUYU_Twilighter
 */
public interface CouponDao extends JpaRepository<Coupon, String> {
    @Query(nativeQuery = true,
        value = "select * from coupon where promotion = ?1 limit ?2")
    List<Coupon> get(String promotionId, int count);
}
