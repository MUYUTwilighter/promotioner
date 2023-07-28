package cool.muyucloud.promotioner.util;

import cool.muyucloud.promotioner.PromotionerApplication;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.Date;

/**
 * @author MUYU_Twilighter
 */
@Component
public class AuthUtil {
    public static final int EXPIRE = 600;

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    public boolean expire(String token, long time) {
        long expire = new Date().getTime();
        expire += time * 1000;
        Date dateExpire = new Date(expire);
        return Boolean.TRUE.equals(redisTemplate.expireAt(token, dateExpire));
    }

    public String put(Long id) {
        String token = RandomString.make(16);
        redisTemplate.opsForValue().set(token, id);
        boolean result = this.expire(token, EXPIRE);
        return result ? token : "[ERROR]";
    }

    public Long get(String token) {
        this.expire(token, EXPIRE);
        return redisTemplate.opsForValue().get(token);
    }

    public boolean exists(String token) {
        return redisTemplate.opsForValue().get(token) != null;
    }
}
