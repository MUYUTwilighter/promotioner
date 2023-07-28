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
    /**
     * Vague search promotions that is available during a date range
     *
     * @param promotionName name of the promotions, using SQL string-like format
     * @param category      category of the promotions
     * @param creator       name of the creator of the promotions, using SQL string-like format
     * @param start         start of a date-range
     * @param end           end of a date-range
     *
     * @return a list of promotions that matches input conditions
     * */
    List<Promotion> vagueSearch(String promotionName, Integer category, String creator, Date start, Date end);

    /**
     * Get specific promotion with its ID
     *
     * @param id ID of the promotion
     *
     * @return {@code Promotion.EMPTY} if not found
     * */
    Promotion queryById(Long id);

    /**
     * Create a promotion
     *
     * @param creator   user ID of the creator
     * @param name      name of the promotion
     * @param category  category of the promotion, see constants in {@link Promotion} for detail
     * @param business  business wher the promotion belongs to
     * @param start     date when promotion become available
     * @param end       date when promotion become unavailable
     *
     * @return true if the promotion successfully created
     * */
    boolean createPromotion(
        Long creator,
        String name,
        Integer category,
        String business,
        Date start,
        Date end
    );

    /**
     * Primary-approve a promotion
     *
     * @param id        ID of the promotion to get approval
     * @param approver  user ID of the approver
     *
     * @return true if the approval successful
     * */
    boolean primaryApprove(Long id, User approver);

    /**
     * Secondary-approve a promotion
     *
     * @param id        ID of the promotion to get approval
     * @param approver  user ID of the approver
     *
     * @return true if the approval successful
     * */
    boolean secondaryApprove(Long id, User approver);
}
