package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.city;
import domain.route;
import domain.scenery;
import service.impl.routeServiceImpl;
import service.routeService;
import util.ObjectSortUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 展示路线详情信息
* */
public class showRouteInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取路线信息
        HttpSession session=request.getSession();
        route route = (route) session.getAttribute("route");

        routeService routeService=new routeServiceImpl();

        Map<String,Object[]> map=new HashMap<String,Object[]>();

        if(route!=null){
            //根据路线获取路线基本信息
            String route_id=route.getRoute_id();
            String route_name=route.getRoute_name();
            String route_createTime=route.getRoute_createTime();
            String route_avatar=route.getRoute_avatar();
            String route_label=route.getRoute_label();
            String route_detail=route.getRoute_detail();
            String start_time=route.getStart_time();
            int spend_time=route.getSpend_time();
            int person_number=route.getPerson_number();
            float route_cost=route.getRoute_cost();
            //根据路线id获取城市基本信息和城市id
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
                            String scenery_avatar=sortedSceneryList.get(m).getScenery_avatar();
                            float scenery_score=sortedSceneryList.get(m).getScenery_score();
                            String scenery_detail=sortedSceneryList.get(m).getScenery_detail();
                            String scenery_location=sortedSceneryList.get(m).getScenery_location();
                            String scenery_traffic=sortedSceneryList.get(m).getScenery_traffic();
                            String scenery_telephone=sortedSceneryList.get(m).getScenery_telephone();

                            //存储map
                            int sceneryCount=m+1;
                            sceneryMap.put(String.valueOf(sceneryCount),new Object[]{scenery_id,scenery_name,scenery_avatar,scenery_score,scenery_detail,scenery_location,scenery_traffic,scenery_telephone});
                        }
                        cityMap.put(String.valueOf(cityCount),new Object[]{city_id,city_name,sceneryMap});
                    }
                }
                dayMap.put(String.valueOf(j),new Object[]{cityMap});
            }
            map.put("route_info",new Object[]{route_id,route_name,route_createTime,route_avatar,route_label,route_detail,start_time,spend_time,person_number,route_cost,dayMap});

            //去除session中的信息
            session.removeAttribute("route");

            //设置响应的数据格式为json
            response.setContentType("application/json;charset=utf-8");
            //将map转换成json
            ObjectMapper mapper=new ObjectMapper();
            mapper.writeValue(response.getWriter(),map);
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
