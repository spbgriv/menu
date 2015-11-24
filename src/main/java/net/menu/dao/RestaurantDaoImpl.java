package net.menu.dao;

import net.menu.model.Restaurant;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("restaurantDao")
public class RestaurantDaoImpl extends AbstractDao<Integer, Restaurant> implements RestaurantDao {

    @Override
    public Restaurant findById(int id) {
        return getByKey(id);
    }

    @Override
    public Restaurant findByName(String name) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        return (Restaurant) criteria.uniqueResult();
    }
}