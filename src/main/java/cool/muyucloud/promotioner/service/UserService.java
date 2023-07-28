package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author MUYU_Twilighter
 */
@Service
public interface UserService {
    /**
     * User login
     *
     * @param id    user ID
     * @param pwd   user password
     *
     * @return true if success
     * */
    boolean login(Long id, String pwd);

    /**
     * Query info of a user
     *
     * @param id    user ID
     *
     * @return a copy of user entity with pwd hidden
     * */
    User query(Long id);

    /**
     * Register a user
     *
     * @param name  user name
     * @param pwd   password
     * @param auth  authentication level
     *
     * @return true if successfully registered
     * */
    Long register(String name, String pwd, Integer auth);

    /**
     * Modify properties of a user
     *
     * @param uid   ID of the user
     * @param name  name to set
     * @param pwd   password to set
     * @param auth  authentication level to set
     *
     * @return true if success
     * */
    boolean modify(Long uid, String name, String pwd, Integer auth);
}
