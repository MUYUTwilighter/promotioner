package cool.muyucloud.promotioner.controller;

import cool.muyucloud.promotioner.entity.User;
import cool.muyucloud.promotioner.service.UserService;
import cool.muyucloud.promotioner.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author MUYU_Twilighter
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthUtil authUtil;

    @GetMapping("/login")
    public String login(
        @RequestParam("uid") Long uid,
        @RequestParam("pwd") String pwd) {
        boolean result = userService.login(uid, pwd);
        if (result) {
            return authUtil.put(uid);
        } else {
            return "[INCORRECT]";
        }
    }

    @GetMapping("/query")
    public User query(@RequestParam("uid") Long uid) {
        return userService.query(uid);
    }
}
