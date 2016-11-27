package test;

import cn.limbo.dao.UserDao;
import cn.limbo.entity.User;
import cn.limbo.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by limbo on 2016/11/26.
 */
public class HibernateTest {

    @Test
    public void save(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService service = ctx.getBean(UserService.class);
        service.addUser("aaa","111");
    }

}
