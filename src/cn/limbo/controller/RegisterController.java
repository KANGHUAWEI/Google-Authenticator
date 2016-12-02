package cn.limbo.controller;

import cn.limbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by limbo on 2016/11/28.
 */
@Controller
@RequestMapping("/registerController")
public class RegisterController {

    @Autowired
    private UserService userService;

    @RequestMapping("/register.do")
    public String register(HttpServletRequest request, HttpServletResponse response){
        String userName = request.getParameter("registerName").trim();
        String userPassword = request.getParameter("registerPassword").trim();


        if(!userName.equals("") && !userPassword.equals("")){
            if(!userService.isExist(userName)){
                userService.addUser(userName,userPassword);
                return "home";
            }
        }
        return "error";
    }

}
