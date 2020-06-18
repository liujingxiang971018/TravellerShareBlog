package servlet;

import domain.city;
import service.impl.routeServiceImpl;
import service.routeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*删除route_info中的路线和collect_route，like_route，dislike_route中的路线*/
public class deleteRouteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取routeid
        String route_id=request.getParameter("route_id");

        routeService routeService=new routeServiceImpl();
        //根据routeid删除like_route中的信息
        routeService.delLikeRoutes(route_id);
        //根据routeId删除dislike_route中的信息
        routeService.delDislikeRoutes(route_id);
        //根据routeId删除collect_route中的信息
        routeService.delCollectRoutes(route_id);
        //根据routeId删除route_info,city_info,scenery_info中的信息
        List<city> cityList = routeService.getCityInfoByRouteId(route_id);
        for(int i=0;i<cityList.size();i++){
            String city_id=cityList.get(i).getCity_id();
            //删除scenery_info中的信息
            routeService.delSceneryInfoByCityId(city_id);
        }
        //删除city_info中的信息
        routeService.delCityInfoByRouteId(route_id);
        //删除route_info中的信息
        routeService.delRouteInfoByRouteId(route_id);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
