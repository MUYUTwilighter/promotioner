package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.dao.CouponDao;
import cool.muyucloud.promotioner.dao.PromotionDao;
import cool.muyucloud.promotioner.entity.Coupon;
import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author MUYU_Twilighter
 */
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private PromotionDao promotionDao;
    @Autowired
    private CouponDao couponDao;

    @Override
    public boolean add(String couponName, Double value, Long pid, Date start, Date end) {
        if (ObjectUtil.anyNull(couponName, value, pid, start, end)) {
            return false;
        }
        if (!validatePromotion(pid, start, end)) {
            return false;
        }
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponName);
        coupon.setValue(value);
        coupon.setStart(start);
        coupon.setEnd(end);
        coupon.setUsed(false);
        couponDao.save(coupon);
        return true;
    }

    @Override
    public boolean use(Long id) {
        Optional<Coupon> optionalCoupon = couponDao.findById(id);
        if (!optionalCoupon.isPresent()) {
            return false;
        }
        Coupon coupon = optionalCoupon.get();
        if (coupon.isUsed()) {
            return false;
        } else {
            coupon.setUsed(true);
            couponDao.save(coupon);
            return true;
        }
    }

    @Override
    public List<Coupon> get(Long pid, String name, int count) {
        List<Coupon> coupons = couponDao.get(pid, name, count);
        if (coupons.size() != count) {
            return null;
        } else {
            return coupons;
        }
    }

    private boolean validatePromotion(Long promotionId, Date start, Date end) {
        Optional<Promotion> optionalPromotion = promotionDao.findById(promotionId);
        if (!optionalPromotion.isPresent()) {
            return false;
        }
        Promotion promotion = optionalPromotion.get();
        return promotion.getStartDate().after(start) && promotion.getEndDate().before(end) && promotion.getSecondaryApprover() != null;
    }
}
