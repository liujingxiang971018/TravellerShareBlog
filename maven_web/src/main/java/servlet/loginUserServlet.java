package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.user;
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
import java.util.HashMap;
import java.util.Map;

public class loginUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数email和password
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //获取存在session中的信息
        HttpSession session=request.getSession();
        user user= null;
        try {
            user = (user)session.getAttribute("forgetUser");
        } catch (Exception e) {
            System.out.println("不存在session");
        }

        String tempPassword= null;
        if(user != null){
            tempPassword = user.getUser_password();
            System.out.println("密码:"+tempPassword);
        }

        userService service=new userServiceImpl();
        boolean b=service.isFirstLogin(email);


        //map用户数据返回
        Map<String,Object> map=new HashMap<String ,Object>();

        //判断是否存在临时密码
        if(tempPassword!=null){
            //存在临时密码
            if(password.equals(tempPassword)){
                //跳转至首页/信息完善页
                map.put("login_success",true);
                //判断是否是第一次的登录
                if(b){
                    map.put("isFirstLogin",true);
                }else{
                    map.put("isFirstLogin",false);
                }

                //登录成功后，将email和密码写入redis中，持久化
                Jedis jedis= JedisPoolUtils.getJedis();
                jedis.hset(email,"email",email);
                jedis.hset(email,"password",password);
                jedis.close();

                session.setAttribute("email",email);

            }else{
                //临时密码已过期
                map.put("login_success",false);
                map.put("error_msg","临时密码已过期，请重新获取");

            }
        }else{
            //不存在临时密码，直接查找数据库
            //调用service查询
            user general_user = service.verifyEmailExist(email);
            if(password.equals(general_user.getUser_password())){
                //跳转至首页/信息完善页
                map.put("login_success",true);
                //判断是否是第一次的登录
                if(b){
                    map.put("isFirstLogin",true);
                }else{
                    map.put("isFirstLogin",false);
                }

                //登录成功后，将email写入redis中，持久化
                Jedis jedis= JedisPoolUtils.getJedis();
                jedis.hset(email,"email",email);
                jedis.hset(email,"password",password);
                jedis.close();

                session.setAttribute("email",email);

            }else{
                map.put("login_success",false);
                map.put("error_msg","密码输入错误");
            }
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
