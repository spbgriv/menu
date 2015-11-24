package net.menu.service;


import net.menu.model.User;

public interface UserService {

	User findById(int id);

    User findByUserName(String username);
	
}