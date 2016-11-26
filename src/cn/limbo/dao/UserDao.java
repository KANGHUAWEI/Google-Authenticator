package cn.limbo.dao;

import cn.limbo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by limbo on 2016/11/26.
 */

public interface UserDao {

    public User getUserByID(String ID);

    public List<User> getAllUsers();

    public void addUser(User user);

    public void deleteUserByID(String ID);

    public void updateUser(User user);

}
