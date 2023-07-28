package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.dao.PromotionDao;
import cool.muyucloud.promotioner.dao.UserDao;
import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.entity.User;
import cool.muyucloud.promotioner.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author MUYU_Twilighter
 */
@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionDao promotionDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<Promotion> vagueSearch(String promotionName, Integer category, String creator, Date start, Date end) {
        return promotionDao.vagueSearch(promotionName, category, creator, start, end);
    }

    @Override
    public Promotion queryById(Long id) {
        return promotionDao.findById(id).orElse(Promotion.EMPTY);
    }

    @Override
    public boolean createPromotion(
        Long creator,
        String name,
        Integer category,
        String business,
        Date start,
        Date end
    ) {
        if (ObjectUtil.anyNull(creator, name, category, business, start, end)) {
            return false;
        }
        Optional<User> optionalUser = userDao.findById(creator);
        User creatorUser = optionalUser.orElse(null);
        if (creatorUser == null) {
            return false;
        }
        Promotion promotion = new Promotion();
        promotion.setPromotionName(name);
        promotion.setCategory(category);
        promotion.setBusiness(business);
        promotion.setCreator(creatorUser);
        promotion.setStartDate(start);
        promotion.setEndDate(end);
        promotionDao.save(promotion);
        return true;
    }

    @Override
    public boolean primaryApprove(Long id, User approver) {
        Promotion promotion = promotionDao.getReferenceById(id);
        if (promotion.getPrimaryApprover() == null) {
            promotion.setPrimaryApprover(approver);
            promotionDao.save(promotion);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean secondaryApprove(Long id, User approver) {
        Promotion promotion = promotionDao.getReferenceById(id);
        if (promotion.getPrimaryApprover() != null &&
            promotion.getSecondaryApprover() == null) {
            promotion.setSecondaryApprover(approver);
            promotionDao.save(promotion);
            return true;
        } else {
            return false;
        }
    }
}
