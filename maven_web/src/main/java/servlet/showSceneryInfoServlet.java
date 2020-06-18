package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.scenery;
import domain.scenery_comment;
import domain.user;
import service.impl.userServiceImpl;
import service.userService;
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

/*展示景点和评论*/
public class showSceneryInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取信息
        HttpSession session=request.getSession();
        scenery scenery=(domain.scenery)session.getAttribute("scenery");
        //获取登录者email
        String email = (String)session.getAttribute("email");

        List<scenery_comment> sceneryCommentList=(List<scenery_comment>)session.getAttribute("sceneryCommentList");

        //对评论按照发布时间排序
        List<scenery_comment> sortsceneryCommentList= ObjectSortUtils.sortComment(sceneryCommentList);

        //创建service
        userService userService=new userServiceImpl();

        Map<String,Object[]> map=new HashMap<>();
        //根据scenery获取信息
        String scenery_id=scenery.getScenery_id();
        String scenery_name=scenery.getScenery_name();
        float scenery_score=scenery.getScenery_score();
        String scenery_avatar=scenery.getScenery_avatar();
        String scenery_detail=scenery.getScenery_detail();
        String scenery_location=scenery.getScenery_location();
        String scenery_traffic=scenery.getScenery_traffic();
        String scenery_telephone=scenery.getScenery_telephone();

        map.put("scenery",new Object[]{scenery_id,scenery_name,scenery_score,scenery_avatar,scenery_detail,scenery_location,scenery_traffic,scenery_telephone});

        Map<String,Object[]> comment=new HashMap<>();
        //根据sceneryCommentList获取信息
        for(int i=0;i<sortsceneryCommentList.size();i++){
            String user_id=sortsceneryCommentList.get(i).getUser_id();
            //根据user_id获取信息
            user user=userService.verifyEmailExist(user_id);
            String user_nickname=user.getUser_nickname();
            String user_avatar=user.getUser_avatar();
            String comment_date=sortsceneryCommentList.get(i).getComment_date();
            String comment_detail=sortsceneryCommentList.get(i).getComment_detail();
            boolean isUser=false;
            if(user_id.equals(email)){
                isUser=true;
            }
            int count=i+1;
            comment.put(String.valueOf(count),new Object[]{user_id,user_nickname,user_avatar,comment_date,comment_detail,isUser});
        }
        map.put("comment",new Object[]{comment});
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
