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
/*
* 对喜欢表like_route和route_info表进行CURD
* */
public class addLikeRouteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String likeType = request.getParameter("like_type");//add,del
        String routeId = request.getParameter("route_id");
        //获取用户id
        HttpSession httpSession=request.getSession();
        String email = (String) httpSession.getAttribute("email");

        routeService routeService=new routeServiceImpl();
        if(likeType.equals("add")){
            //修改route_info中的like_number
            routeService.addRouteLikeNumber(routeId);
            //给like_route添加一条记录
            routeService.addLikeRoute(email,routeId);
        }
        if(likeType.equals("del")){
            //修改route_info中的like_number
            routeService.delRouteLikeNumber(routeId);
            //给like_route删除一条记录
            routeService.delLikeRoute(email,routeId);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
