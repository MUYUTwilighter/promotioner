package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.dao.CouponDao;
import cool.muyucloud.promotioner.dao.PromotionDao;
import cool.muyucloud.promotioner.entity.Coupon;
import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
    public void add(String name, Double value, Integer promotionId, Date start, Date end) {
        if (ObjectUtil.anyNull(name, value, promotionId, start, end)) {
            return;
        }
        if (validatePromotion(promotionId, start, end)) {
            Coupon coupon = new Coupon();
            coupon.setName(name);
            coupon.setValue(value);
            coupon.setStart(start);
            coupon.setEnd(end);
            coupon.setUsed(false);
            couponDao.save(coupon);
        }
    }

    @Override
    public boolean use(Long id) {
        Optional<Coupon> optionalCoupon = couponDao.findById(id);
        if (optionalCoupon.isEmpty()) {
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

    private boolean validatePromotion(Integer promotionId, Date start, Date end) {
        Optional<Promotion> optionalPromotion = promotionDao.findById(promotionId);
        if (optionalPromotion.isEmpty()) {
            return false;
        }
        Promotion promotion = optionalPromotion.get();
        return promotion.getStart().after(start) && promotion.getEnd().before(end) && promotion.getSecondaryApprover() != null;
    }
}