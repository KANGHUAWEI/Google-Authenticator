package cn.limbo.service.impl;

import cn.limbo.dao.UserDao;
import cn.limbo.entity.User;
import cn.limbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by limbo on 2016/11/26.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByID(String ID) {
        return userDao.getUserByID(ID);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public void addUser(String name,String password) {
        userDao.addUser(new User(name,password));
    }

    @Override
    public void deleteUserByID(String ID) {
        userDao.deleteUserByID(ID);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public boolean isExist(String userName) {
        if(userDao.getUserByName(userName) != null)
            return true;
        return false;
    }
}
