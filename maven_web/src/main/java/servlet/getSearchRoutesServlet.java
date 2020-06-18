package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getSearchRoutesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取searchRoutesServlet保存到session中数据
        HttpSession session=request.getSession();
        List<route> searchRouteList = (List<route>)session.getAttribute("searchRouteList");
        String inputText=(String)session.getAttribute("inputText");
        int currentPage=(Integer)session.getAttribute("currentPage");
        String type=(String)session.getAttribute("type");
        int totalNumber=(Integer)session.getAttribute("totalNumber");
        String sortType=(String)session.getAttribute("sortType");

        //获取email
        String email = (String) session.getAttribute("email");
        //获取service
        routeService routeService=new routeServiceImpl();

        //组装map
        Map<String,Object> map=new HashMap<String, Object>();
        int count=0;
        for(route searchItem:searchRouteList){
            //页面上需要route_id,route_avatar,route_name,route_label,route_detail,user_nickname(user_id),like_number,dislike_number,collect_number;
            String route_id=searchItem.getRoute_id();
            String route_avatar=searchItem.getRoute_avatar();
            String route_name=searchItem.getRoute_name();
            String route_label=searchItem.getRoute_label();
            String route_detail=searchItem.getRoute_detail();
            String user_id=searchItem.getUser_id();
            int like_number=searchItem.getLike_number();
            int dislike_number=searchItem.getDislike_number();
            int collect_number=searchItem.getCollect_number();

            //根据创建者的user_id查询user_info，获取用户昵称
            userService userService=new userServiceImpl();
            user user = userService.verifyEmailExist(user_id);
            String user_nickname=user.getUser_nickname();

            //根据登陆者的email和route_id查询like_route和dislike_route和collect_route,判断选中状态
            boolean like_status=routeService.getLikeStatus(email,route_id);
            boolean dislike_status=routeService.getDislikeStatus(email,route_id);
            boolean collect_status=routeService.getCollectStatus(email,route_id);

            count+=1;
            map.put(String.valueOf(count), new Object[]{route_id, route_avatar, route_label, route_name,route_detail,user_nickname,like_status,like_number,dislike_status,dislike_number,collect_status,collect_number,currentPage});
        }
        map.put("inputText",inputText);
        map.put("type",type);
        map.put("totalNumber",totalNumber);
        map.put("sortType",sortType);

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
