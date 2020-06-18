package servlet;

import domain.city;
import domain.scenery;
import org.apache.commons.beanutils.BeanUtils;
import service.impl.routeServiceImpl;
import service.routeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class updateSceneryInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//1.设置编码
        request.setCharacterEncoding("utf-8");

        //获取参数信息
        Map<String, String[]> scenerymap = request.getParameterMap();
        //3.封装对象
        scenery scenery=new scenery();
        try {
            BeanUtils.populate(scenery,scenerymap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service
        routeService routeService=new routeServiceImpl();
        routeService.updateScenery(scenery);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
