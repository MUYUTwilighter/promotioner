package cool.muyucloud.promotioner.controller;

import cool.muyucloud.promotioner.entity.Coupon;
import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.entity.User;
import cool.muyucloud.promotioner.service.CouponService;
import cool.muyucloud.promotioner.service.PromotionService;
import cool.muyucloud.promotioner.service.UserService;
import cool.muyucloud.promotioner.util.AuthoriseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Controller
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthoriseUtil authoriseUtil;

    @PostMapping("/add")
    public Boolean addCoupon(
        @RequestParam(value = "token") String token,
        @RequestParam(value = "name") String name,
        @RequestParam(value = "value") Double value,
        @RequestParam(value = "promotion") String promotionId,
        @RequestParam(value = "start") Date start,
        @RequestParam(value = "end") Date end,
        @RequestParam(value = "count", defaultValue = "1") Integer count) {
        if (!authoriseUtil.exists(token)) {
            return false;
        }
        String uid = authoriseUtil.get(token);
        User user = userService.query(uid);
        Promotion promotion = promotionService.queryById(promotionId);
        if (promotion == null) {
            return false;
        }
        if (promotion.getCreator().equals(user.getId())) {
            return false;
        }
        if (count <= 0) {
            return false;
        }
        for (int i = 0; i < count; ++i) {
            couponService.add(name, value, promotionId, start, end);
        }
        return true;
    }

    @GetMapping("/get")
    public List<Coupon> getCoupon(
        @RequestParam(value = "promotion") String promotion,
        @RequestParam(value = "count", defaultValue = "1") Integer count) {
        if (count <= 0) {
            return null;
        } else {
            return couponService.get(promotion, count);
        }
    }
}
