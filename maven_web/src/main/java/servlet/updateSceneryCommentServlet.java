package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.scenery_comment;
import domain.user;
import org.apache.commons.beanutils.BeanUtils;
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
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*保存评论*/
public class updateSceneryCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //获取email
        HttpSession httpSession=request.getSession();
        String email = (String) httpSession.getAttribute("email");

        String scenery_id=request.getParameter("scenery_id");
        String comment_detail=request.getParameter("comment_detail");
        //3.封装对象
        scenery_comment comment=new scenery_comment();

        comment.setUser_id(email);
        comment.setScenery_id(scenery_id);
        comment.setComment_detail(comment_detail);
        //获取日期
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String formatDate=sdf.format(date);
        comment.setComment_date(formatDate);

        userService userService=new userServiceImpl();
        user user=userService.verifyEmailExist(email);
        String user_avatar=user.getUser_avatar();
        String user_nickname=user.getUser_nickname();

        //获取service
        routeService routeService=new routeServiceImpl();
        routeService.updateComment(comment);

        //获取头像、昵称、评论日期、评论内容 返回给前端
        Map<String,String[]> map=new HashMap<>();
        map.put("comment",new String[]{user_avatar,user_nickname,formatDate,comment_detail});

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
