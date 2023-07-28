package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.PromotionerApplication;
import cool.muyucloud.promotioner.dao.UserDao;
import cool.muyucloud.promotioner.entity.User;
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
}
