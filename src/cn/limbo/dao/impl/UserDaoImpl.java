package cn.limbo.dao.impl;

import cn.limbo.dao.UserDao;
import cn.limbo.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by limbo on 2016/11/26.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

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
    public List<User> getAllUsers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        return criteria.list();
    }

    @Override
    public void addUser(User user) {
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
