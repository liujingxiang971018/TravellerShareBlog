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

/*
* 获取用户的昵称,并存入redis中
* */
public class getUserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession=request.getSession();
        String email = (String) httpSession.getAttribute("email");

        //从redis中获取email
        Jedis jedis= JedisPoolUtils.getJedis();
        String password=jedis.hget(email,"password");
        String nickname=jedis.hget(email,"nickname");
        String sex=jedis.hget(email,"sex");
        String city=jedis.hget(email,"city");
        String birthday=jedis.hget(email,"birthday");
        String profile=jedis.hget(email,"profile");
        String avatar=jedis.hget(email,"avatar");

        //如果redis中有数据，则从redis中读取；如果没有数据，从数据库中查询，再写入redis
        if(sex==null || city==null || birthday==null || profile==null || avatar==null || nickname==null){
            //调用service查询
            userService service=new userServiceImpl();
            user user = service.verifyEmailExist(email);

            nickname=user.getUser_nickname();
            sex=user.getUser_sex();
            city=user.getUser_city();
            birthday=user.getUser_birthday();
            profile=user.getUser_profile();
            avatar=user.getUser_avatar();

            /*jedis.set("nickname",email);
            jedis.set("sex",sex);
            jedis.set("city",city);
            jedis.set("birthday",birthday);
            jedis.set("profile",profile);
            jedis.set("avatar",avatar);*/
        }

        //map用户数据返回
        Map<String,Object> map=new HashMap<String ,Object>();

        map.put("email",email);
        map.put("password",password);

        map.put("nickname",nickname);
        map.put("sex",sex);
        map.put("city",city);
        map.put("birthday",birthday);
        map.put("profile",profile);
        map.put("avatar",avatar);

        jedis.close();

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
