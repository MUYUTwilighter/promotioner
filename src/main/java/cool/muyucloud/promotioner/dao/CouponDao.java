package cool.muyucloud.promotioner.dao;

import cool.muyucloud.promotioner.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author MUYU_Twilighter
 */
public interface CouponDao extends JpaRepository<Coupon, Long> {
    @Query(nativeQuery = true,
        value = "select * from coupon where promotion = ?1 and couponName = ?2 limit ?3")
    List<Coupon> get(Long pid, String name, int count);
}
