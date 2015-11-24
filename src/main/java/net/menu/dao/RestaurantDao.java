package net.menu.dao;

import net.menu.model.Restaurant;

public interface RestaurantDao {

    Restaurant findById(int id);

    Restaurant findByName(String name);

}