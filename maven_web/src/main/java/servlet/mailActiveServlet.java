package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.user;
import util.UuidUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class mailActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数code

        String code = request.getParameter("verify_code");

        HttpSession session=request.getSession();
        user user = (user)session.getAttribute("user");

        //map用户数据返回
        Map<String,Object> map=new HashMap<String ,Object>();

        if(code.equals(user.getUser_code())){
            //将用户激活状态设为Y
            user.setUser_state("Y");
            //更新session
            session.setAttribute("user",user);
            map.put("verify_result",true);
        }else{
            //激活码不一样，存在风险操作，重新验证
            //将用户激活状态设为N
            user.setUser_state("N");
            //更新session
            session.setAttribute("user",user);
            map.put("verify_result",false);
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
