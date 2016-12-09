package cn.limbo.dao.impl;

import cn.limbo.dao.UserDao;
import cn.limbo.entity.User;
import cn.limbo.utils.MD5Util;
import org.hibernate.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by limbo on 2016/11/26.
 */
@Repository("userDao")
@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 根据ID获取用户实体
     * @param ID 用户ID
     * @return 返回用户实体
     */
    @Override
    public User getUserByID(String ID) {
//        Query query = sessionFactory.getCurrentSession().createQuery("from User u WHERE u.ID = ?");
//        query.setString(0,ID);
//        List<User> users = query.list();
//        System.out.println(users.toArray());
        User user = (User) sessionFactory.getCurrentSession().get(User.class,ID);
        return user;
    }

    @Override
    public User getUserByName(String userName) {
        String hql = "FROM User u WHERE u.name = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0,userName);
        if(query.list().size() != 0)
            return (User) query.list().get(0);
        return null;
    }


    @Override
    public List<User> getAllUsers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        return criteria.list();
    }

    @Override
    public void addUser(User user) {
        try {
            user.setPassword(MD5Util.encode2Hex(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void deleteUserByID(String ID) {
        User user = getUserByID(ID);
        sessionFactory.getCurrentSession().delete(user);
    }

    @Override
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }
}
