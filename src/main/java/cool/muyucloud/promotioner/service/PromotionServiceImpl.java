package cool.muyucloud.promotioner.service;

import cool.muyucloud.promotioner.dao.PromotionDao;
import cool.muyucloud.promotioner.entity.Promotion;
import cool.muyucloud.promotioner.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionDao promotionDao;

    @Override
    public List<Promotion> vagueSearch(String name, Integer category, String creator, Date start, Date end) {
        return promotionDao.vagueSearch(name, category, creator, start, end);
    }

    @Override
    public Promotion queryById(Integer id) {
        return promotionDao.getReferenceById(id);
    }

    @Override
    public boolean createPromotion(
        String creator,
        String name,
        Integer category,
        String business,
        Date start,
        Date end
    ) {
        if (ObjectUtil.anyNull(creator, name, category, business, start, end)) {
            return false;
        }
        Promotion promotion = new Promotion();
        promotion.setName(name);
        promotion.setCategory(category);
        promotion.setBusiness(business);
        promotion.setCreator(creator);
        promotion.setStart(start);
        promotion.setEnd(end);
        promotionDao.save(promotion);
        return true;
    }

    @Override
    public boolean primaryApprove(Integer id, Integer approver) {
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
    public boolean secondaryApprove(Integer id, Integer approver) {
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
