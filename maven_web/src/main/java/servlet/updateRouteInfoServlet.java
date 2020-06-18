package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.route;
import domain.user;
import org.apache.commons.beanutils.BeanUtils;
import service.impl.routeServiceImpl;
import service.routeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class updateRouteInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");

        //获取email
        HttpSession httpSession=request.getSession();
        String email = (String) httpSession.getAttribute("email");

        //获取参数信息
        Map<String, String[]> routemap = request.getParameterMap();
        //3.封装对象
        route route=new route();
        try {
            BeanUtils.populate(route,routemap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        route.setUser_id(email);

        //调用service
        routeService routeService=new routeServiceImpl();

        //更新或者插入路线
        routeService.updateRoute(route);
        //删除路线下的city和scenery
        routeService.deleteCityAndScenery(route);

        //返回前端，提示已完成
        Map<String,Boolean> map=new HashMap<>();
        map.put("Success",true);

        //设置响应的数据格式为json
        response.setContentType("application/json;charset=utf-8");
        //将map转换成json
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(response.getWriter(),map);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
