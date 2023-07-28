package cool.muyucloud.promotioner.controller;

import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.entity.User;
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
@RequestMapping("/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthUtil authoriseUtil;

    @GetMapping("/search/id")
    public Promotion searchId(@RequestParam(value = "id") Long id) {
        return promotionService.queryById(id);
    }

    @GetMapping("/search/vague")
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
        @RequestParam(value = "name") String name,
        @RequestParam(value = "category", defaultValue = "0") Integer category,
        @RequestParam(value = "business") String business,
        @RequestParam(value = "start") Date start,
        @RequestParam(value = "end") Date end) {
        if (!authoriseUtil.exists(token)) {
            return false;
        }
        Long uid = authoriseUtil.get(token);
        User user = userService.query(uid);
        if (!User.isStaff(user)) {
            return false;
        }
        return promotionService.createPromotion(user.getUid(), name, category, business, start, end);
    }

    @PutMapping("/approve/primary")
    public Boolean primaryApprove(
        @RequestParam(value = "token") String token,
        @RequestParam(value = "pid") Long pid) {
        if (!authoriseUtil.exists(token)) {
            return false;
        }
        Long uid = authoriseUtil.get(token);
        User user = userService.query(uid);
        if (!User.isPrimaryStaff(user)) {
            return false;
        }
        return promotionService.primaryApprove(pid, user);
    }

    @PutMapping("/approve/secondary")
    public Boolean secondaryApprove(
        @RequestParam(value = "token") String token,
        @RequestParam(value = "pid") Long pid) {
        if (!authoriseUtil.exists(token)) {
            return false;
        }
        Long uid = authoriseUtil.get(token);
        User user = userService.query(uid);
        if (!User.isSecondaryStaff(user)) {
            return false;
        }
        return promotionService.secondaryApprove(pid, user);
    }
}
