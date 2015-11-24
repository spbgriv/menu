package net.menu.dao;

import net.menu.model.User;

public interface UserDao {

    User findById(int id);

	User findByUserName(String username);

}