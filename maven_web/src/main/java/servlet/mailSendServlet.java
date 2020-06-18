package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.user;
import util.MailUtils;
import util.UuidUtils;
import util.VerifyCodeUtils;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class mailSendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求参数user
        String email = request.getParameter("email");

        user user=new user();
        user.setUser_id(email);
        String verifyCode = VerifyCodeUtils.getVerifyCode();
        user.setUser_code(verifyCode);

        //设置用户的session，时间为30分钟
        HttpSession session=request.getSession();
        session.setAttribute("user",user);//默认失效时间30分钟

        //给用户发送邮件
        String content="游书-旅游分享平台：<br>" +
                "本邮件用于注册账号时进行验证邮箱，邮件30分钟内有效，" +
                "验证码为："+verifyCode+"<br>" +
                "如非本人操作，请忽略。";
        boolean b = MailUtils.sendMail(email, content, "verify your email");

        //map用户数据返回
        Map<String,Object> map=new HashMap<String ,Object>();

        map.put("sendSuccess",b);

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
