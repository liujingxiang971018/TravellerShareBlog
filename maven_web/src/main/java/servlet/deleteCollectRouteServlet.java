package servlet;

import service.impl.routeServiceImpl;
import service.routeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/*根据routeId和email删除collect_route中的记录*/
public class deleteCollectRouteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取routeId
        String route_id=request.getParameter("route_id");
        //获取email
        HttpSession session=request.getSession();
        String email = (String) session.getAttribute("email");

        routeService routeService=new routeServiceImpl();
        routeService.delCollectRoute(email,route_id);

        //将route_info中收藏数量减一
        routeService.subRouteCollectNumber(route_id);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
