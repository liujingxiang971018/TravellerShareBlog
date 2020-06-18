package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.user;
import service.impl.userServiceImpl;
import service.userService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class registerUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");

        //获取密码，创建调用userService，创建用户
        String password = request.getParameter("password");
        HttpSession session=request.getSession();
        user user=(user)session.getAttribute("user");
        user.setUser_password(password);

        //调用service
        userService service=new userServiceImpl();
        boolean b=service.createUser(user);

        //map用户数据返回
        Map<String,Object> map=new HashMap<String ,Object>();
        map.put("createUserSuccess",b);
        //设置响应的数据格式为json
        response.setContentType("application/json;charset=utf-8");
        //将map转换成json
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(response.getWriter(),map);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
