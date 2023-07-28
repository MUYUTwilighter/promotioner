package cool.muyucloud.promotioner.controller;

import cool.muyucloud.promotioner.entity.Coupon;
import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.entity.User;
import cool.muyucloud.promotioner.service.CouponService;
import cool.muyucloud.promotioner.service.PromotionService;
import cool.muyucloud.promotioner.service.UserService;
import cool.muyucloud.promotioner.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthUtil authoriseUtil;

    @PostMapping("/add")
    public Integer addCoupon(
        @RequestParam(value = "token") String token,
        @RequestParam(value = "name") String name,
        @RequestParam(value = "value") Double value,
        @RequestParam(value = "pid") Long pid,
        @RequestParam(value = "start") Date start,
        @RequestParam(value = "end") Date end,
        @RequestParam(value = "count", defaultValue = "1") Integer count) {
        int success = 0;
        if (!authoriseUtil.exists(token)) {
            return success;
        }
        Long uid = authoriseUtil.get(token);
        User user = userService.query(uid);
        Promotion promotion = promotionService.queryById(pid);
        if (promotion == null) {
            return success;
        }
        if (promotion.getCreator().getUid().equals(user.getUid())) {
            return success;
        }
        if (count <= 0) {
            return success;
        }
        for (int i = 0; i < count; ++i) {
            boolean result = couponService.add(name, value, pid, start, end);
            if (result) {
                ++success;
            }
        }
        return success;
    }

    @GetMapping("/get")
    public List<Coupon> getCoupon(
        @RequestParam(value = "pid") Long pid,
        @RequestParam(value = "name") String name,
        @RequestParam(value = "count", defaultValue = "1") Integer count) {
        if (count <= 0) {
            return Coupon.EMPTY_LIST;
        } else {
            return couponService.get(pid, name, count);
        }
    }
}
