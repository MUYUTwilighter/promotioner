package cool.muyucloud.promotioner.dao;

import cool.muyucloud.promotioner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {
}
