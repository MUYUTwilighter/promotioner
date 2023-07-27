package cool.muyucloud.promotioner.util;

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
    private static final SecureRandom RANDOM = new SecureRandom();

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
        this.expire(token, EXPIRE);
        return token;
    }

    public Long get(String token) {
        this.expire(token, EXPIRE);
        return redisTemplate.opsForValue().get(token);
    }

    public boolean exists(String token) {
        return redisTemplate.opsForValue().get(token) != null;
    }

    private static String generateToken() {
        long figure = RANDOM.nextLong();
        String token = Long.toUnsignedString(figure, 16);
        short len = (short) token.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < (16 - len); ++i) {
            builder.append(0);
        }
        builder.append(token);
        return builder.toString();
    }
}
