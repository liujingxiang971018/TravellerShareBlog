package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.city;
import domain.route;
import domain.scenery;
import domain.user;
import service.impl.routeServiceImpl;
import service.routeService;
import util.ObjectSortUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*获取route和user详细信息，展示给路线详情页面*/
public class showRouteDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取route和user
        HttpSession session=request.getSession();
        route route=(route)session.getAttribute("route");
        user user=(user)session.getAttribute("user");

        //从session中获取登陆者email
        String loginEmail=(String)session.getAttribute("email");

        routeService routeService=new routeServiceImpl();

        //组装map
        Map<String,Object[]> map=new HashMap<>();
        //获取用户信息
        String user_nickname=user.getUser_nickname();
        String user_avatar=user.getUser_avatar();
        String user_id=user.getUser_id();
        map.put("user",new Object[]{user_nickname,user_avatar});

        //获取路线信息
        String route_id=route.getRoute_id();
        String route_name=route.getRoute_name();
        String route_createTime=route.getRoute_createTime();
        String route_avatar=route.getRoute_avatar();
        String start_time=route.getStart_time();
        int spend_time=route.getSpend_time();
        int people_number=route.getPerson_number();
        float route_cost=route.getRoute_cost();
        int like_number=route.getLike_number();
        int dislike_number=route.getDislike_number();
        int collect_number=route.getCollect_number();

        //获取登录用户是否点击喜欢、不喜欢、收藏
        //根据登陆者的email和route_id查询like_route和dislike_route和collect_route,判断选中状态
        boolean like_status= routeService.getLikeStatus(loginEmail,route_id);
        boolean dislike_status=routeService.getDislikeStatus(loginEmail,route_id);
        boolean collect_status=routeService.getCollectStatus(loginEmail,route_id);

        //根据route_id获取城市信息
        List<city> cityList=routeService.getCityInfoByRouteId(route_id);
        //先根据start_time进行排序,再根据start_count进行排序
        List<city> sortedCityList= ObjectSortUtils.sortCity(cityList);

        //获取总天数
        int totalDay=0;
        for(int i=0;i<sortedCityList.size();i++){
            totalDay=sortedCityList.get(i).getStart_time();
        }

        Map<String,Object[]> dayMap=new HashMap<>();
        for(int j=1;j<=totalDay;j++){

            Map<String,Object[]> cityMap=new HashMap<>();
            for(int k=0;k<sortedCityList.size();k++){

                if(sortedCityList.get(k).getStart_time()==j){

                    //得到city_id,获取景点集合
                    String city_id=sortedCityList.get(k).getCity_id();
                    List<scenery> sceneryList=routeService.getSceneryInfoByCityId(city_id);
                    //获取城市的基本信息
                    String city_name=sortedCityList.get(k).getCity_name();

                    Map<String,Object[]> sceneryMap=new HashMap<>();

                    int cityCount=sortedCityList.get(k).getStart_count();
                    //根据start_count进行排序
                    List<scenery> sortedSceneryList=ObjectSortUtils.sortScenery(sceneryList);
                    for(int m=0;m<sortedSceneryList.size();m++){
                        //获取景点的基本信息
                        String scenery_id=sortedSceneryList.get(m).getScenery_id();
                        String scenery_name=sortedSceneryList.get(m).getScenery_name();

                        //存储map
                        int sceneryCount=sortedSceneryList.get(m).getStart_count();
                        sceneryMap.put(String.valueOf(sceneryCount),new Object[]{scenery_id,scenery_name});
                    }
                    cityMap.put(String.valueOf(cityCount),new Object[]{city_id,city_name,sceneryMap});
                }
            }
            dayMap.put(String.valueOf(j),new Object[]{cityMap});

        }
        map.put("route",new Object[]{route_name,route_createTime,route_avatar,start_time,spend_time,people_number,route_cost,like_number,like_status,dislike_number,dislike_status,collect_number,collect_status,dayMap,route_id});


        /*//去除session中的信息
        session.removeAttribute("user");
        session.removeAttribute("route");*/

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
