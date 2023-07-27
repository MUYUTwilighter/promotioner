package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.entity.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Service
public interface PromotionService {
    List<Promotion> vagueSearch(String promotionName, Integer category, String creator, Date start, Date end);

    Promotion queryById(Long id);

    boolean createPromotion(
        Long creator,
        String name,
        Integer category,
        String business,
        Date start,
        Date end
    );

    boolean primaryApprove(Long id, User approver);

    boolean secondaryApprove(Long id, User approver);
}
