package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.route;
import domain.user;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 获取指定页面的最新发表的路线
* */
public class getLastestItemsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //首先从redis中获取email
        HttpSession httpSession=request.getSession();
        String email = (String) httpSession.getAttribute("email");

        int currentPage=Integer.parseInt(request.getParameter("currentPage"));

        routeService routeService=new routeServiceImpl();
        List<route> routeList=routeService.getLastestItems(currentPage);

        //返回到页面上
        //map用户数据返回
        Map<Integer,Object> map=new HashMap<Integer ,Object>();
        int count=0;
        for(route lastestItem:routeList){
            //页面上需要route_id,route_avatar,route_name,route_label,route_detail,user_nickname(user_id),like_number,dislike_number,collect_number;
            String route_id=lastestItem.getRoute_id();
            String route_avatar=lastestItem.getRoute_avatar();
            String route_name=lastestItem.getRoute_name();
            String route_label=lastestItem.getRoute_label();
            String route_detail=lastestItem.getRoute_detail();
            String user_id=lastestItem.getUser_id();
            int like_number=lastestItem.getLike_number();
            int dislike_number=lastestItem.getDislike_number();
            int collect_number=lastestItem.getCollect_number();

            //根据创建者的user_id查询user_info，获取用户昵称
            userService userService=new userServiceImpl();
            user user = userService.verifyEmailExist(user_id);
            String user_nickname=user.getUser_nickname();

            //根据登陆者的email和route_id查询like_route和dislike_route和collect_route,判断选中状态
            boolean like_status=routeService.getLikeStatus(email,route_id);
            boolean dislike_status=routeService.getDislikeStatus(email,route_id);
            boolean collect_status=routeService.getCollectStatus(email,route_id);

            count+=1;
            map.put(count, new Object[]{route_id, route_avatar, route_label, route_name,route_detail,user_nickname,like_status,like_number,dislike_status,dislike_number,collect_status,collect_number});
        }

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
