package cool.muyucloud.promotioner.controller;

import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.service.CouponService;
import cool.muyucloud.promotioner.service.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Controller
@RequestMapping("/upcoming")
public class UpcomingPromotionController {
    private PromotionService promotionService;
    private CouponService couponService;

    @GetMapping("/search/id")
    @ResponseBody
    public Promotion searchId(Integer id) {
        return promotionService.queryById(id);
    }

    @GetMapping("/search/vague")
    @ResponseBody
    public List<Promotion> vagueSearch(
        @RequestParam(value = "name") String name,
        @RequestParam(value = "category") Integer category,
        @RequestParam(value = "creator") String creator,
        @RequestParam(value = "start") Date start,
        @RequestParam(value = "end") Date end) {
        return promotionService.vagueSearch(name, category, creator, start, end);
    }

    @PostMapping("/promotion/create")
    public Boolean createPromotion(
        @RequestParam(value = "creator") String creator,
        @RequestParam(value = "name") String name,
        @RequestParam(value = "category") Integer category,
        @RequestParam(value = "business") String business,
        @RequestParam(value = "start") Date start,
        @RequestParam(value = "end") Date end) {
        return promotionService.createPromotion(creator, name, category, business, start, end);
    }

    @PutMapping("/promotion/approve/primary")
    public Boolean primaryApprove(
        @RequestParam(value = "id") Integer id,
        @RequestParam(value = "approver") Integer approver) {
        return promotionService.primaryApprove(id, approver);
    }

    @PutMapping("/promotion/approve/secondary")
    public Boolean secondaryApprove(
        @RequestParam(value = "id") Integer id,
        @RequestParam(value = "approver") Integer approver) {
        return promotionService.secondaryApprove(id, approver);
    }

    @PostMapping("/coupon/add")
    public Boolean addCoupon(
        @RequestParam(value = "name") String name,
        @RequestParam(value = "value") Double value,
        @RequestParam(value = "promotion") Integer promotion,
        @RequestParam(value = "start") Date start,
        @RequestParam(value = "end") Date end,
        @RequestParam(value = "count") Integer count) {
        if (count <= 0) {
            return false;
        }
        for (int i = 0; i < count; ++i) {
            couponService.add(name, value, promotion, start, end);
        }
        return true;
    }
}
