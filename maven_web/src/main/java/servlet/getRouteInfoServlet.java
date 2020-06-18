package servlet;

import domain.route;
import service.impl.routeServiceImpl;
import service.routeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/*根据路线id获取路线信息*/
public class getRouteInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String route_id = request.getParameter("route_id");
        //根据id查询
        routeService routeService=new routeServiceImpl();
        route route=routeService.getRouteInfoByRouteId(route_id);

        //存入session
        HttpSession session=request.getSession();
        session.setAttribute("route",route);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
