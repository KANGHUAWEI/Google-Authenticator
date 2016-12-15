package test;

import cn.limbo.dao.UserDao;
import cn.limbo.entity.User;
import cn.limbo.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by limbo on 2016/11/26.
 */
public class HibernateTest {

    @Test
    public void save(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService service = ctx.getBean(UserService.class);
        EntityManagerFactory e = ctx.getBean(EntityManagerFactory.class);
//        service.addUser("ccc","11243dsa");
//
//        service.deleteUserByID(3);
//
//        User u = service.getUserByID(4);
//        u.setName("bbb");
//        service.updateUser(u);
        List<User> users = service.getAllUsers();
        System.out.println(users);
        User u = service.getUserByID(1);
        System.out.println(u);
        e.close();
        List<User> users2 = service.getAllUsers();
        System.out.println(users2);
        User u1 = service.getUserByID(1);
        System.out.println(u1);
        //service.isExist("wyh");
    }

}
