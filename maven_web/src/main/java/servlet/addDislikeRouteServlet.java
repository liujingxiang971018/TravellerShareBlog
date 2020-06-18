package servlet;

import redis.clients.jedis.Jedis;
import service.impl.routeServiceImpl;
import service.routeService;
import util.JedisPoolUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class addDislikeRouteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String likeType = request.getParameter("like_type");//add,del
        String routeId = request.getParameter("route_id");
        //获取用户id
        HttpSession httpSession=request.getSession();
        String email = (String) httpSession.getAttribute("email");

        routeService routeService=new routeServiceImpl();
        if(likeType.equals("add")){
            //修改route_info中的dislike_number
            routeService.addRouteDislikeNumber(routeId);
            //给dislike_route添加一条记录
            routeService.addDislikeRoute(email,routeId);
        }
        if(likeType.equals("del")){
            //修改route_info中的dislike_number
            routeService.delRouteDislikeNumber(routeId);
            //给dislike_route删除一条记录
            routeService.delDislikeRoute(email,routeId);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
