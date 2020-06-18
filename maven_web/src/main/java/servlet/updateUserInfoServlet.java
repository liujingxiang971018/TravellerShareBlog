package servlet;

import domain.user;
import org.apache.commons.beanutils.BeanUtils;
import redis.clients.jedis.Jedis;
import service.impl.userServiceImpl;
import service.userService;
import util.JedisPoolUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//更新用户上传的数据
public class updateUserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数usermap
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //获取email
        HttpSession httpSession=request.getSession();
        String email = (String) httpSession.getAttribute("email");

        //2.获取map
        Map<String, String[]> usermap = request.getParameterMap();
        //3.封装对象
        user user=new user();
        try {
            BeanUtils.populate(user,usermap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        user.setUser_id(email);

        //4.调用service修改
        userService service=new userServiceImpl();
        service.updateUser(user);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
