package cool.muyucloud.promotioner.controller;

import cool.muyucloud.promotioner.entity.User;
import cool.muyucloud.promotioner.service.UserService;
import cool.muyucloud.promotioner.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author MUYU_Twilighter
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthUtil authoriseUtil;

    @GetMapping("/login")
    public String login(
        @RequestParam("uid") Long uid,
        @RequestParam("pwd") String pwd) {
        boolean result = userService.login(uid, pwd);
        if (result) {
            return authoriseUtil.put(uid);
        } else {
            return null;
        }
    }

    @GetMapping("/query")
    public User query(@RequestParam("uid") Long uid) {
        return userService.query(uid);
    }
}
