package cool.muyucloud.promotioner.controller;

import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.entity.User;
import cool.muyucloud.promotioner.service.PromotionService;
import cool.muyucloud.promotioner.service.UserService;
import cool.muyucloud.promotioner.util.AuthoriseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Controller
@RequestMapping("/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthoriseUtil authoriseUtil;

    @GetMapping("/search/id")
    @ResponseBody
    public Promotion searchId(@RequestParam(value = "id") String id) {
        return promotionService.queryById(id);
    }

    @GetMapping("/search/vague")
    @ResponseBody
    public List<Promotion> vagueSearch(
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "category", required = false) Integer category,
        @RequestParam(value = "creator", required = false) String creator,
        @RequestParam(value = "start", required = false) Date start,
        @RequestParam(value = "end", required = false) Date end) {
        return promotionService.vagueSearch(name, category, creator, start, end);
    }

    @PostMapping("/create")
    public Boolean createPromotion(
        @RequestParam(value = "token") String token,
        @RequestParam(value = "creator") String creator,
        @RequestParam(value = "name") String name,
        @RequestParam(value = "category") Integer category,
        @RequestParam(value = "business") String business,
        @RequestParam(value = "start") Date start,
        @RequestParam(value = "end") Date end) {
        if (!authoriseUtil.exists(token)) {
            return false;
        }
        String uid = authoriseUtil.get(token);
        User user = userService.query(uid);
        if (!user.isStaff()) {
            return false;
        }
        return promotionService.createPromotion(creator, name, category, business, start, end);
    }

    @PutMapping("/approve/primary")
    public Boolean primaryApprove(
        @RequestParam(value = "token") String token,
        @RequestParam(value = "promotionId") String promotionId) {
        if (!authoriseUtil.exists(token)) {
            return false;
        }
        String uid = authoriseUtil.get(token);
        User user = userService.query(uid);
        if (!user.isPrimaryStaff()) {
            return false;
        }
        return promotionService.primaryApprove(promotionId, user.getUid());
    }

    @PutMapping("/approve/secondary")
    public Boolean secondaryApprove(
        @RequestParam(value = "token") String token,
        @RequestParam(value = "promotionId") String id) {
        if (!authoriseUtil.exists(token)) {
            return false;
        }
        String uid = authoriseUtil.get(token);
        User user = userService.query(uid);
        if (!user.isSecondaryStaff()) {
            return false;
        }
        return promotionService.secondaryApprove(id, user.getUid());
    }
}
