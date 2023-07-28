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
}
