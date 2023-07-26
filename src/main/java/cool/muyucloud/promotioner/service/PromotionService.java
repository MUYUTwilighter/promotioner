package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.entity.Promotion;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Service
public interface PromotionService {
    List<Promotion> vagueSearch(String name, Integer category, String creator, Date start, Date end);

    Promotion queryById(String id);

    boolean createPromotion(
        String creator,
        String name,
        Integer category,
        String business,
        Date start,
        Date end
    );

    boolean primaryApprove(String id, String approver);

    boolean secondaryApprove(String id, String approver);
}
