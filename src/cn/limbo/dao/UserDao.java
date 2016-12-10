package cn.limbo.dao;

import cn.limbo.entity.User;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by limbo on 2016/11/26.
 */

@RepositoryDefinition(domainClass = User.class, idClass = Integer.class)
public interface UserDao{

    @Query("select u from User u where u.ID = ?1")
    public User getUserByID(int ID);

    @Query("select u from User u where u.name = ?1")
    public User getUserByName(String userName);

    @Query("select u from User u")
    public List<User> getAllUsers();

    public void save(User user);

    @Modifying
    @Query("delete from User u where u.ID = ?1")
    public void delete(int ID);

    @Modifying
    @Query("update User u set u.name = :name , u.password = :password where u.ID = :ID")
    public void update(@Param("ID") Integer ID ,@Param("name")  String name , @Param("password") String password);

}
