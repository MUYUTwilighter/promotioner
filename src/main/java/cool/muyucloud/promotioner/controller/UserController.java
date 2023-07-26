package cool.muyucloud.promotioner.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import cool.muyucloud.promotioner.service.UserService;
import cool.muyucloud.promotioner.util.AuthoriseUtil;
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
    AuthoriseUtil authoriseUtil;

    @GetMapping("/login")
    public String login(
        @RequestParam("uid") String uid,
        @RequestParam("pwd") String pwd) {
        boolean result = userService.login(uid, pwd);
        if (result) {
            return authoriseUtil.put(uid);
        } else {
            return null;
        }
    }
}
