package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.dao.UserDao;
import cool.muyucloud.promotioner.entity.User;
import cool.muyucloud.promotioner.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author MUYU_Twilighter
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public boolean login(Long id, String pwd) {
        Optional<User> optionalUser = userDao.findById(id);
        if (!optionalUser.isPresent()) {
            return false;
        }
        User user = optionalUser.get();
        return user.getPwd().equals(pwd);
    }

    @Override
    public User query(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.genHidden();
        } else {
            return User.EMPTY;
        }
    }

    @Override
    public boolean register(String name, String pwd, Integer auth) {
        if (ObjectUtil.anyNull(name, pwd, auth)) {
            return false;
        }
        User user = new User();
        user.setUserName(name);
        user.setPwd(pwd);
        user.setAuth(auth);
        userDao.save(user);
        return true;
    }

    @Override
    public boolean modify(Long uid, String name, String pwd, Integer auth) {
        if (ObjectUtil.anyNull(name, pwd, auth)) {
            return false;
        }
        Optional<User> optionalUser = userDao.findById(uid);
        if (!optionalUser.isPresent()) {
            return false;
        }
        User user = optionalUser.get();
        user.setUserName(name);
        user.setPwd(pwd);
        user.setAuth(auth);
        userDao.save(user);
        return true;
    }
}
