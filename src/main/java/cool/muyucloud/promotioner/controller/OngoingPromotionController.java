package cool.muyucloud.promotioner.controller;

import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.service.CouponService;
import cool.muyucloud.promotioner.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Controller
@RequestMapping("/ongoing")
public class OngoingPromotionController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private CouponService couponService;

    @PostMapping("/coupon/use")
    public Boolean useCoupon(@RequestParam(value = "id") Long id) {
        return couponService.use(id);
    }
}
