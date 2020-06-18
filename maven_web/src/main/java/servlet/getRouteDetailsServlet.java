package servlet;

import domain.route;
import domain.user;
import service.impl.routeServiceImpl;
import service.impl.userServiceImpl;
import service.routeService;
import service.userService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/*获取路线详情*/
public class getRouteDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数 route_id
        String route_id=request.getParameter("route_id");

        //调用service
        routeService routeService=new routeServiceImpl();
        userService userService=new userServiceImpl();
        //查询route
        route route=routeService.getRouteInfoByRouteId(route_id);
        String email=route.getUser_id();
        //根据user_id查询创建者信息
        user user=userService.verifyEmailExist(email);

        //写入session，供showRouteDetailsServlet使用
        HttpSession session=request.getSession();
        session.setAttribute("route",route);
        session.setAttribute("user",user);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
