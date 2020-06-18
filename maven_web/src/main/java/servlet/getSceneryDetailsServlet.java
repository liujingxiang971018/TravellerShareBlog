package servlet;

import domain.scenery;
import domain.user;
import domain.scenery_comment;
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
import java.util.List;

public class getSceneryDetailsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取scenery_id和创建者user_id
        String scenery_id=request.getParameter("scenery_id");

        //获取登录者email
        HttpSession session=request.getSession();

        //创建service
        routeService routeService=new routeServiceImpl();
        //根据scenery_id获取对象
        scenery scenery=routeService.getSceneryInfoBySceneryId(scenery_id);

        //根据scenery_id获取评论表
        List<scenery_comment> sceneryCommentList=routeService.getSceneryCommentListBySceneryId(scenery_id);

        session.setAttribute("scenery",scenery);
        session.setAttribute("sceneryCommentList",sceneryCommentList);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
