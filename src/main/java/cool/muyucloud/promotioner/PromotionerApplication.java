package cool.muyucloud.promotioner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author MUYU_Twilighter
 */
@SpringBootApplication
@EnableCaching
public class PromotionerApplication {
	public static void main(String[] args) {
		SpringApplication.run(PromotionerApplication.class, args);
	}
}
