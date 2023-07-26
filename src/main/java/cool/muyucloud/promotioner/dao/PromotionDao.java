package cool.muyucloud.promotioner.dao;

import cool.muyucloud.promotioner.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * @author MUYU_Twilighter
 */
public interface PromotionDao extends JpaRepository<Promotion, String> {
    @Query(nativeQuery = true,
        value = "select * from promotion " +
            "where (name like ?1 or ?1 is NULL) " +
            "   and (category = ?2 or ?2 is NULL) " +
            "   and (creator like ?3 or ?3 is NULL) " +
            "   and (end >= ?4 or ?4 is NULL) " +
            "   and (start <= ?5 or ?5 is NULL)")
    List<Promotion> vagueSearch(String name, Integer category, String creator, Date start, Date end);
}
