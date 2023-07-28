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

    @PostMapping("/register")
    public Boolean register(
        @RequestParam("token") String token,
        @RequestParam("name") String name,
        @RequestParam("pwd") String pwd,
        @RequestParam("auth") Integer auth) {
        if (authUtil.exists(token)) {
            Long uid = authUtil.get(token);
            User user = query(uid);
            if (!User.isDataBaseAdmin(user)) {
                return false;
            }
            if ((~user.getAuth() & auth) != 0) {
                return false;
            }
            userService.register(name, pwd, auth);
            return true;
        } else {
            return false;
        }
    }

    @PutMapping("/modify/auth")
    public Boolean modifyAuth(
        @RequestParam("token") String token,
        @RequestParam("uid") Long uid,
        @RequestParam("auth") Integer auth) {
        Long modifierId = authUtil.get(token);
        if (modifierId == null) {
            return false;
        }
        User modifier = userService.query(modifierId);
        User user = userService.query(uid);
        if (user == null || modifier.getAuth() <= user.getAuth()) {
            return false;
        }
        return userService.modify(uid, user.getUserName(), user.getPwd(), auth);
    }

    @PutMapping("/modify/property")
    public Boolean modifyProperty(
        @RequestParam("token") String token,
        @RequestParam("name") String name,
        @RequestParam("pwd") String pwd) {
        Long uid = authUtil.get(token);
        if (uid == null) {
            return false;
        }
        User user = userService.query(uid);
        return userService.modify(uid, name, pwd, user.getAuth());
    }
}
