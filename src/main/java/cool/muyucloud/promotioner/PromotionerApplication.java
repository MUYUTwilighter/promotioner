package cool.muyucloud.promotioner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.logging.Logger;

/**
 * @author MUYU_Twilighter
 */
@SpringBootApplication
@EnableCaching
public class PromotionerApplication {
	public static final Logger LOGGER = Logger.getLogger("Promotioner");

	public static void main(String[] args) {
		SpringApplication.run(PromotionerApplication.class, args);
	}
}
