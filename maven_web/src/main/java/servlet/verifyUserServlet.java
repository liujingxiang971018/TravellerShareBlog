package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.user;
import service.impl.userServiceImpl;
import service.userService;
import util.UuidUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class verifyUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String email = request.getParameter("email");
        //调用service查询
        userService service=new userServiceImpl();
        user user = service.verifyEmailExist(email);


        //map用户数据返回
        Map<String,Object> map=new HashMap<String ,Object>();

        if(user==null){
            //email未注册
            map.put("email",email);
            map.put("userExist",false);
        }else{
            //email已注册
            map.put("userExist",true);
        }
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
