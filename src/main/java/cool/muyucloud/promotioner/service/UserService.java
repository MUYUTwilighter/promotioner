package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author MUYU_Twilighter
 */
@Service
public interface UserService {
    boolean login(Long id, String pwd);

    User query(Long id);
}
