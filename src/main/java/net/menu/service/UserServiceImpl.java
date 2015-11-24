package net.menu.service;

import net.menu.dao.UserDao;
import net.menu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by griv.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;

	public User findById(int id) {
		return dao.findById(id);
	}

    @Override
    public User findByUserName(String username) {
        return dao.findByUserName(username);
    }

}
