package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.route;
import redis.clients.jedis.Jedis;
import service.impl.routeServiceImpl;
import service.impl.userServiceImpl;
import service.routeService;
import service.userService;
import util.JedisPoolUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/*
* 执行推荐算法，获取推荐的五个路线
*
* 基于路线的推荐：
*   计算路线之间的相似度
*   根据路线的相似度和用户的历史行为给用户生成推荐列表
*
* */
public class getRecommendItemsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //首先从redis中获取email
        HttpSession httpSession=request.getSession();
        String email = (String) httpSession.getAttribute("email");

        //计算物品之间的相似度，削弱活跃度对物品相似度的影响。
        //得到物品相似度矩阵之后，计算用户id为email对物品j的兴趣值,给用户推荐前5个最感兴趣的物品
        routeService service=new routeServiceImpl();
        List<route> recommendItems=service.getItemSimilarityMatrix(email);

        //返回到页面上
        //map用户数据返回
        Map<Integer,Object> map=new HashMap<Integer ,Object>();
        int count=0;
        for(route recommendItem:recommendItems){
            //页面上需要route_id,route_avatar,route_name,route_label
            String route_id=recommendItem.getRoute_id();
            String route_avatar=recommendItem.getRoute_avatar();
            String route_name=recommendItem.getRoute_name();
            String route_label=recommendItem.getRoute_label();
            count+=1;
            map.put(count, new String[]{route_id, route_avatar, route_label, route_name});
        }

        //设置响应的数据格式为json
        response.setContentType("application/json;charset=utf-8");
        //将map转换成json
        ObjectMapper mapper=new ObjectMapper();
        mapper.writeValue(response.getWriter(),map);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
