package cn.limbo.controller;

import cn.limbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 登陆控制器
 * Created by limbo on 2016/11/28.
 */
@Controller
@RequestMapping("/loginController")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 判断用户登录
     * @param request 请求
     * @param response 响应
     * @return 返回对应视图的名称
     */
    @RequestMapping("/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String userName = request.getParameter("userName").trim();
        String userPassword = request.getParameter("userPassword").trim();

        if(!userName.equals("") && !userPassword.equals("")) {
            if(userService.isExist(userName))
                return "admin";
        }
        return "error";
    }

    @RequestMapping("/isExist.do")
    public void isExist(HttpServletRequest request, HttpServletResponse response){
        String userName = request.getParameter("userName").trim();
        Writer writer = null;
        try {
            writer = response.getWriter();
            if(!userService.isExist(userName)){
                writer.write("fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
